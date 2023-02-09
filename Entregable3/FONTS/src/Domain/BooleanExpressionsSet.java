package Domain;

import std_extend.Pair;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public class BooleanExpressionsSet {
    private static TreeMap<Integer, BooleanExpression> bExpressions;

    public BooleanExpressionsSet() {
        bExpressions = new TreeMap<>();
    }

    public BooleanExpressionsSet(ArrayList<String> data) {
        bExpressions = new TreeMap<>();
        int act = 0;
        try {
            for (String be : data) {
                bExpressions.put(act++, new BooleanExpression(be));
            }
        } catch (Exception ignored) {
        }
    }

    public int add(String s) {
        int act = 0;
        if (bExpressions.size() > 0) act = bExpressions.lastKey() + 1;
        try {
            bExpressions.put(act, new BooleanExpression(s));
        } catch (Exception e) {
            return -1;
        }
        return act;
    }

    public void remove(int n) {
        bExpressions.remove(n);
    }

    public ArrayList<Pair<Integer, String>> list() {
        ArrayList<Pair<Integer, String>> result = new ArrayList<>();
        for (Map.Entry<Integer, BooleanExpression> p : bExpressions.entrySet()) {
            result.add(new Pair<>(p.getKey(), p.getValue().getExpression()));
        }
        return result;
    }

    public static Boolean evaluate(int id, Document doc) {
        return bExpressions.get(id).evaluate(doc);
    }

    public ArrayList<String> serialize() {
        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<Integer, BooleanExpression> p : bExpressions.entrySet()) {
            result.add(p.getValue().getExpression());
        }
        return result;
    }
}
