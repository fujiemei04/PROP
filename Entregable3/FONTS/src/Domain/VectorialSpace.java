package Domain;

import std_extend.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class VectorialSpace {

    private ArrayList<Document> library;

    public VectorialSpace() {
        library = new ArrayList<>();
    }

    public void clean() {
        library = new ArrayList<>();
    }


    public void add(Document doc) {
        library.add(doc);
    }

    public void setReference(Document reference) {
        library.clear();
        library.add(reference);
    }

    private boolean exists_term(Document doc, String term) {
        ArrayList<String> text = doc.getPlainText();
        for (String s : text) {
            String[] words = s.replaceAll("^[.,\\s]+", "").split("[.,\\s]+");
            for (String word : words) {
                if (word != null && !word.equals(""))
                    if (word.equals(term)) return true;
            }
        }
        return false;
    }

    private TreeMap<String, Integer> termFreq(Document doc) {
        ArrayList<String> text = doc.getPlainText();
        TreeMap<String, Integer> term_frequency = new TreeMap<>();
        if (text.size() == 0) term_frequency.put("\0", 1);
        else
            for (String s : text) {
                String[] words = s.replaceAll("^[.,\\s]+", "").split("[.,\\s]+");
                for (String word : words) {
                    if (word != null && !word.equals("")) {
                        if (!term_frequency.containsKey(word)) {
                            term_frequency.put(word, 1);
                        } else term_frequency.put(word, term_frequency.get(word) + 1);
                    }
                }
            }
        return term_frequency;
    }

    private TreeMap<String, Double> document_weight(Document doc) {
        TreeMap<String, Double> doc_weight = new TreeMap<>();
        TreeMap<String, Integer> term_frequency = termFreq(doc);
        for (Map.Entry<String, Integer> a : term_frequency.entrySet()) {
            double doc_freq_in_term = 0.0;
            for (Document d : library)
                if (exists_term(d, a.getKey()))
                    doc_freq_in_term = doc_freq_in_term + 1.0;
            double number_of_docs = library.size();
            double tfdi = a.getValue();
            double idfi = Math.log((number_of_docs) / doc_freq_in_term);
            double wdi = tfdi * idfi;
            doc_weight.put(a.getKey(), wdi);
        }
        return doc_weight;
    }

    private void addAuthorTitle(TreeMap<Double, AuthorTitleList> similarity, Double sim, Document doc) {
        AuthorTitleList a = new AuthorTitleList();
        if (similarity.containsKey(sim)) {
            ArrayList<Pair<String, String>> b = similarity.get(sim).getAuthorTitle();
            for (Pair<String, String> c : b) {
                a.addNames(c.getKey(), c.getValue());
            }
        }
        a.add(doc);
        similarity.put(sim, a);
    }

    private TreeMap<Double, AuthorTitleList> vector_space_model(Document doc) {
        TreeMap<Double, AuthorTitleList> similarity = new TreeMap<>();
        TreeMap<String, Double> doc_weight = document_weight(doc);
        for (Document docs : library) {
            if (!docs.equals(doc)) {
                TreeMap<String, Double> doc_to_compare_weight = document_weight(docs);
                double scalar = 0.0, norm1 = 0.0, norm2 = 0.0;
                for (Map.Entry<String, Double> d : doc_weight.entrySet()) {
                    if (doc_to_compare_weight.containsKey(d.getKey()))
                        scalar += d.getValue() * doc_to_compare_weight.get(d.getKey());
                    norm1 += d.getValue() * d.getValue();
                }
                for (Map.Entry<String, Double> d : doc_to_compare_weight.entrySet())
                    norm2 += d.getValue() * d.getValue();
                Double sim = scalar / Math.sqrt(norm1 * norm2);
                addAuthorTitle(similarity, sim, docs);
            }
        }
        return similarity;
    }


    public ArrayList<Pair<String, String>> getSimilar(Document doc, int k) {
        TreeMap<Double, AuthorTitleList> similarity = vector_space_model(doc);
        Map<Double, AuthorTitleList> reversemap = similarity.descendingMap();
        ArrayList<Pair<String, String>> similar = new ArrayList<>();
        int i = 0;
        Iterator<Map.Entry<Double, AuthorTitleList>> iterator = reversemap.entrySet().iterator();
        while (iterator.hasNext() && i < k) {
            Map.Entry<Double, AuthorTitleList> entry = iterator.next();
            ArrayList<Pair<String, String>> sims = entry.getValue().getAuthorTitle();
            for (Pair<String, String> c : sims) {
                if (i < k) {
                    similar.add(new Pair<>(c.getValue(), c.getKey()));
                    ++i;
                }
            }
        }
        return similar;
    }
}