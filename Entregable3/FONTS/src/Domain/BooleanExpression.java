package Domain;

import java.util.ArrayList;
import java.util.Objects;

class Node {
    private String value;
    private Node left;
    private Node right;

    public Node(String value) {
        this.value = value;
        left = null;
        right = null;
    }

    public void setLeft(Node node) {
        left = node;
    }

    public void setRight(Node node) {
        right = node;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public Boolean isLeaf() {
        return right == null && left == null;
    }

    public Boolean hasLeftNode() {
        return !(left == null);
    }

    public Boolean hasRightNode() {
        return !(right == null);
    }

    public void copy(Node n) {
        this.value = n.getValue();
        this.left = n.getLeft();
        this.right = n.getRight();
    }
}


public class BooleanExpression {
    String expression;
    private final Node root;

    public BooleanExpression(String expression) throws Exception {
        this.expression = expression;
        root = new Node("");
        createTree(expression, root);
        correctNot(root);
    }

    public void correctNot(Node node) {
        if (node.hasLeftNode()) {
            correctNot(node.getLeft());
        }
        if (node.hasRightNode()) {
            correctNot(node.getRight());
        }

        String aux = node.getValue();
        int index = 0;
        while (aux.charAt(index) == ' ') ++index;

        if (aux.charAt(index) == '!') {
            ++index;
            int mark = index;
            while (aux.charAt(index) == ' ') ++index;
            char character;

            if (aux.charAt(index) == '(') {
                ++index;
                int count = 1;
                int size = aux.length();
                //System.out.println("size: " + size);
                String symbol = " ";


                while (index < size && symbol.equals(" ")) {
                    character = aux.charAt(index);
                    if (character == '(') ++count;
                    else if (character == ')') --count;
                    else if (count == 1) {
                        if (character == '&') symbol = "&";
                        else if (character == '|') symbol = "|";
                    }
                    ++index;
                }
                if (symbol.equals(" ")) {
                    node.setLeft(new Node(aux.substring(mark)));
                    node.setValue("!");
                } else {
                    Node rightNode = node.getRight();
                    Node leftNode = node.getLeft();
                    node.setValue("!");
                    node.setRight(null);
                    Node nodeSymbol = new Node(symbol);
                    node.setLeft(nodeSymbol);
                    nodeSymbol.setLeft(leftNode);
                    nodeSymbol.setRight(rightNode);
                }
            } else {
                node.setLeft(new Node(aux.substring(mark)));
                node.setValue("!");
            }
        }
    }

    public String getExpression() {
        return expression;
    }

    private Node createTree(String expression, Node node) throws Exception {
        int i = 0, size = expression.length();
        char character;
        while (i < size) {
            character = expression.charAt(i);
            if (character == '&') {
                node.setLeft(createTree(expression.substring(0, i), new Node("")));
                node.setRight(createTree(expression.substring(i + 1, size), new Node("")));
                node.setValue("&");
                return node;
            } else if (character == '|') {
                node.setLeft(createTree(expression.substring(0, i), new Node("")));
                node.setRight(createTree(expression.substring(i + 1, size), new Node("")));
                node.setValue("|");
                return node;
            } else if (character == '(') {
                int aux = i + 1;
                node.copy(createTree(expression.substring(aux, size), new Node("")));
                int count = 1;
                while (count != 0) {
                    if (expression.charAt(i + 1) == '(') ++count;
                    else if (expression.charAt(i + 1) == ')') --count;
                    ++i;
                }
            } else if (character == ')') {
                String tmp = expression.substring(0, i).replaceAll(" ", "");
                if (tmp.charAt(0) != '(') node.setValue(expression.substring(0, i));
                return node;
            }
            ++i;
        }
        String tmp = expression.replaceAll(" ", "");
        if (tmp.charAt(0) != '(') node.setValue(expression);
        return node;
    }

    private Boolean findWord(Document doc, String s) {
        ArrayList<String> text = doc.getPlainText();
        for (String line : text) {
            String[] words = line.replaceAll("^[.,\\s]+", "").split("[.,\\s]+");
            for (String word : words) {
                if (word != null && !word.equals("")) {
                    if (Objects.equals(word.trim(), s.trim())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Boolean quotationMarks(Document doc, String s) {
        StringBuilder aux = new StringBuilder();
        int cont = 0;
        for (int i = 0; i < s.length(); i++) {
            if (cont == 1 && s.charAt(i) != '"') aux.append(s.charAt(i));
            if (s.charAt(i) == '"') ++cont;
        }

        ArrayList<String> text = doc.getPlainText();
        for (String line : text) {
            if (line.length() >= aux.length()) {
                int i = 0;
                while (i < line.length() - aux.length() + 1) {
                    if (line.startsWith(aux.toString(), i)) return true;
                    ++i;
                }
            }
        }
        return false;
    }

    private Boolean brackets(Document doc, String s) {
        int i = 0;
        StringBuilder aux = new StringBuilder();
        while (s.charAt(i) != '{') ++i;
        ++i;
        while (s.charAt(i) != '}') {
            if (s.charAt(i) != ' ') aux.append(s.charAt(i));
            else {
                if (!findWord(doc, aux.toString())) return false;
                aux = new StringBuilder();
            }
            ++i;
        }
        return findWord(doc, aux.toString());
    }

    private boolean treeConversion(Document doc, Node aux) {
        if (!aux.isLeaf()) {
            if (Objects.equals(aux.getValue(), "&")) {
                return treeConversion(doc, aux.getLeft()) && treeConversion(doc, aux.getRight());
            } else if (Objects.equals(aux.getValue(), "|")) {
                return treeConversion(doc, aux.getLeft()) || treeConversion(doc, aux.getRight());
            } else if (Objects.equals(aux.getValue(), "!")) {
                return !treeConversion(doc, aux.getLeft());
            }
        }

        String tmp = aux.getValue().trim();
        char ini = tmp.charAt(0);
        if (ini == '"') {
            return quotationMarks(doc, aux.getValue());
        } else if (ini == '{') {
            return brackets(doc, aux.getValue());
        }
        return findWord(doc, aux.getValue());
    }

    public boolean evaluate(Document doc) {
        return treeConversion(doc, root);
    }
}
