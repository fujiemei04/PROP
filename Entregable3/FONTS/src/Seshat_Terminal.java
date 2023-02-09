import Controllers.ControllerPresentation;
import std_extend.Pair;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Seshat_Terminal {
    //region Usage
    private static void help(String command) {
        if (command == null || command.isEmpty()) {
            helpUsage();
            newAuthorUsage();
            listAuthorsUsage();
            removeAuthorUsage();
            newDocumentUsage();
            listDocumentUsage();
            editDocumentUsage();
            printDocumentUsage();
            removeDocumentUsage();
            removeDocumentUsage();
            findSimilarUsage();
            newBooleanExpressionUsage();
            listBooleanExpressionUsage();
            removeBooleanExpressionUsage();
            advancedSearchUsage();
            exitUsage();
        } else {
            switch (command) {
                case "help":
                    helpUsage();
                    break;
                case "newAuthor":
                    newAuthorUsage();
                    break;
                case "listAuthors":
                    listAuthorsUsage();
                    break;
                case "removeAuthors":
                    removeAuthorUsage();
                    break;
                case "newDocument":
                    newDocumentUsage();
                    break;
                case "listDocument":
                    listDocumentUsage();
                    break;
                case "editDocument":
                    editDocumentUsage();
                    break;
                case "printDocument":
                    printDocumentUsage();
                    break;
                case "removeDocument":
                    removeDocumentUsage();
                    break;
                case "exit":
                    exitUsage();
                    break;
                case "findSimilar":
                    findSimilarUsage();
                    break;
                case "newBooleanExpression":
                    newBooleanExpressionUsage();
                    break;
                case "listBooleanExpression":
                    listBooleanExpressionUsage();
                    break;
                case "removeBooleanExpression":
                    removeBooleanExpressionUsage();
                    break;
                case "advancedSearch":
                    advancedSearchUsage();
                    break;
                default:
                    System.out.println(command + " is not recognized");
            }
        }
    }

    private static void helpUsage() {
        System.out.println("Help.");
        System.out.println("usage: \n\thelp [<command>]\n\th [<command>]");
        System.out.println("Show all commands' usage.");
        System.out.println("\t<command>: Indicates the command to show information about.\n");
    }

    private static void newAuthorUsage() {
        System.out.println("Add new author.");
        System.out.println("usage: \n\tnewAuthor author_name...\n\tna author_name...");
        System.out.println("Adds every 'author_name' to the authors list. System will notify if Author was added successfully or the reason of why it was not added.\n");
    }

    private static void listAuthorsUsage() {
        System.out.println("List authors.");
        System.out.println("usage: \n\tlistAuthors [<prefix>]\n\tla [<prefix>]");
        System.out.println("List authors saved in the system.");
        System.out.println("\t<prefix>: Indicates the prefix to filter author list with.\n");
    }

    private static void newDocumentUsage() {
        System.out.println("New document.");
        System.out.println("usage: \n\tnewDocument document_author document_title\n\tnd document_author document_title");
        System.out.println("Create new document.");
        System.out.println("document_author: Indicates the new document author's name.");
        System.out.println("document_title: Indicates the new document title.");
        System.out.println("System will notify if the document was created successfully or the reason of why not.\n");
    }

    private static void listDocumentUsage() {
        System.out.println("List documents.");
        System.out.println("usage: \n\tlistDocuments [author_name...]\n\tld [author_name...]");
        System.out.println("List existing documents. If author_name is indicated, only list the author's documents.\n");
    }

    private static void editDocumentUsage() {
        System.out.println("Edit document.");
        System.out.println("usage: \n\teditDocument document_author document_title\n\ted document_author document_title");
        System.out.println("Execute the console interface of document edit.");
        System.out.println("At first it will print the actual text of the file. Then you can write the new data.");
        System.out.println("Use commands <SAVE> and <END> to save the document and end the edition respectively.\n");
    }

    private static void printDocumentUsage() {
        System.out.println("Print document.");
        System.out.println("usage: \n\tprintDocument document_author document_title\n\tpd document_author document_title");
        System.out.println("Print document's content.");
        System.out.println("document_author: Indicates the document author's name.");
        System.out.println("document_title: Indicates the document title.");
        System.out.println("System will notify if the document does not exist.\n");
    }

    private static void removeDocumentUsage() {
        System.out.println("Remove document.");
        System.out.println("usage: \n\tremoveDocument <document_author document_title>...\n\trd <document_author document_title>...");
        System.out.println("Remove document specified by document_author and document_title.\n");
    }

    private static void removeAuthorUsage() {
        System.out.println("Remove authors.");
        System.out.println("usage: \n\tremoveAuthor [author_name...]\n\tra [author_name...]");
        System.out.println("Remove all authors stored.");
        System.out.println("author_name: Indicates what stored authors want to be removed. System will notify if the Authors were removed successfully or the reason of why they were not removed.\n");
    }

    private static void findSimilarUsage() {
        System.out.println("Find similar.");
        System.out.println("usage: \n\tfindSimilar document_author document_title n\n\tfs findSimilar document_author document_title n");
        System.out.println("Search n similar documents.");
        System.out.println("document_author: Indicates the author of the reference document.\n");
        System.out.println("document_title: Indicates the title of the reference document.\n");
        System.out.println("n: Indicates how many documents will the software return.\n");
    }

    private static void newBooleanExpressionUsage() {
        System.out.println("New boolean expression.");
        System.out.println("usage: \n\tnewBooleanExpression boolean_expression\n\tnbe boolean_expression");
        System.out.println("Add new boolean expression. And return the id of the new boolean expression.");
        System.out.println("boolean_expression: is the new boolean expression to add\n");
    }

    private static void listBooleanExpressionUsage() {
        System.out.println("List boolean expression.");
        System.out.println("usage: \n\tlistBooleanExpression \n\tlbe");
        System.out.println("List all boolean expression stored with its identifier.");
    }

    private static void removeBooleanExpressionUsage() {
        System.out.println("Remove boolean expression.");
        System.out.println("usage: \n\tremoveBooleanExpression n\n\trbe n");
        System.out.println("Remove boolean expression with id n.");
        System.out.println("n: indicate the boolean expression to remove.\n");
    }

    private static void advancedSearchUsage() {
        System.out.println("Advanced search");
        System.out.println("usage: \n\tadvancedSearch \n\tas n");
        System.out.println("Search all the documents that satisfy n boolean expression.");
        System.out.println("n: Indicates the id of the boolean expression\n");
    }

    private static void exitUsage() {
        System.out.println("Exit.");
        System.out.println("usage: \n\texit");
        System.out.println("Close the application properly.");
    }
    //endregion

    private static String formCmd(String s) {
        if (s == null) return "";
        switch (s) {
            case ("h"):
                return "help";
            case ("na"):
                return "newAuthor";
            case ("la"):
                return "listAuthors";
            case ("ra"):
                return "removeAuthors";
            case ("nd"):
                return "newDocument";
            case ("ld"):
                return "listDocuments";
            case ("ed"):
                return "editDocument";
            case ("pd"):
                return "printDocument";
            case ("rd"):
                return "removeDocuments";
            case ("fs"):
                return "findSimilar";
            case ("nbe"):
                return "newBooleanExpression";
            case ("lbe"):
                return "listBooleanExpression";
            case ("rbe"):
                return "removeBooleanExpression";
            case ("as"):
                return "advancedSearch";
            default:
                return s;
        }
    }

    public static void main(String[] args) {
        ControllerPresentation cp = new ControllerPresentation();
        //region Intro
        System.out.println("**************************************");
        System.out.println("Welcome to Mains.Seshat ");
        System.out.println("You are using the Alpha version 1.0");
        System.out.println("**************************************");
        System.out.println();
        //endregion
        //Console interface
        Scanner scanner = new Scanner(System.in);
        String[] input = (scanner.nextLine()).split(" ");
        input[0] = formCmd(input[0]);
        StringBuilder aux = new StringBuilder();
        while (!Objects.equals(input[0], "exit")) {
            //System.out.println("Your text: " + command);
            switch (input[0]) {
                case "help":
                    //region help
                    if (input.length == 1) help("");
                    else help(formCmd(input[1]));
                    break;
                //endregion

                case "newAuthor":
                    //region newAuthor
                    if (input.length > 1) {
                        for (int i = 1; i < input.length; ++i) {
                            if (cp.addAuthor(input[i])) {
                                System.out.println("Author '" + input[i] + "' was registered.");
                            } else {
                                System.out.println("Error: '" + input[i] + "' already registered.");
                            }
                        }
                    } else {
                        System.out.println("Error bad use of the command, use 'help [<command>' to get usage information.");
                    }
                    break;
                //endregion

                case "listAuthors":
                    //region listAuthors
                    ArrayList<String> AuthList;
                    if (input.length == 1) {
                        AuthList = cp.listAuthors();
                        if (AuthList != null && AuthList.size() > 0) {
                            System.out.println("Authors:");
                            for (String e : AuthList) {
                                System.out.println("\t - " + e);
                            }
                        } else {
                            System.out.println("There's not exist any author yet");
                        }
                    } else {
                        AuthList = cp.listAuthors(input[1]);
                        if (AuthList.size() > 0) {
                            System.out.println("Authors starting with '" + input[1] + "':");
                            for (String e : AuthList) {
                                System.out.println("\t - " + e);
                            }
                        } else {
                            System.out.println("Any author with '" + input[1] + "' prefix founded.");
                        }
                    }
                    break;
                //endregion

                case "removeAuthors":
                    //region removeAuthors
                    if (input.length == 1) {
                        System.out.println("You have to specify the author name");
                    } else {
                        for (int i = 1; i < input.length; ++i) {
                            if (cp.authorExist(input[i])) {
                                if (cp.haveNotTitles(input[i])) {
                                    if (cp.removeAuthor(input[i]))
                                        System.out.println("Author '" + input[i] + "' was erased.");
                                    else System.out.println("Error: Unknown Error.");
                                } else System.out.println("Error: Author '" + input[i] + "' have documents assigned.");
                            } else System.out.println("Error: Author '" + input[i] + "' it doesn't exist.");
                        }
                    }
                    break;
                //endregion

                case "newDocument":
                    //region newDocument
                    if (input.length == 3) {
                        if (cp.documentExist(input[1], input[2])) {
                            System.out.println("This document with this author exist, can not be created.");
                        } else {
                            if (cp.authorExist(input[1])) {
                                cp.addDocument(input[1], input[2]);
                                System.out.println("Document of author '" + input[1] + "' with title '" + input[2] + "' was added successfully.");
                            } else {
                                System.out.println("Error: author '" + input[1] + "' not found. Do you want to create it [type 'yes' to add].");
                                String test = scanner.nextLine();
                                if (Objects.equals(test, "yes")) {
                                    if (cp.addAuthor(input[1])) {
                                        System.out.println("Author '" + input[1] + "' was registered.");
                                        cp.addDocument(input[1], input[2]);
                                        System.out.println("Document of author '" + input[1] + "' with title '" + input[2] + "' was added successfully.");
                                    } else System.out.println("Error: Adding the Author");
                                } else System.out.println("Document creation aborted.");
                            }
                        }
                    } else {
                        System.out.println("Error bad use of the command, use 'help [<command>' to get usage information.");
                    }
                    break;
                //endregion

                case "listDocuments":
                    //region listDocuments
                    if (input.length == 1) {
                        ArrayList<Pair<String, String>> docs_list = cp.listDocuments();
                        if (docs_list.size() != 0) {
                            String last = docs_list.get(0).getKey();
                            System.out.println("+ " + docs_list.get(0).getKey());
                            for (Pair<String, String> d : docs_list) {
                                if (!Objects.equals(last, d.getKey())) {
                                    System.out.println("+ " + d.getKey());
                                    last = d.getKey();
                                }
                                System.out.println("\t- " + d.getValue());
                            }
                        } else {
                            System.out.println("There is not any document declared");
                        }
                    } else {
                        for (int i = 1; i < input.length; ++i) {
                            if (cp.authorExist(input[i])) {
                                ArrayList<String> docs_list = cp.listDocuments(input[i]);
                                if (docs_list.size() != 0) {
                                    System.out.println("+ " + input[i]);
                                    for (String d : docs_list) {
                                        System.out.println("\t- " + d);
                                    }
                                } else {
                                    System.out.println("Author '" + input[i] + "' have not any document yet.");
                                }
                            } else {
                                System.out.println("Author '" + input[i] + "' not exist.");
                            }
                        }
                    }
                    break;
                //endregion

                case "editDocument":
                    //region editDocument
                    if (input.length == 3) {
                        if (cp.documentExist(input[1], input[2])) {
                            ArrayList<String> text = cp.loadDocument(input[1], input[2]);
                            System.out.println("Actual text:");
                            System.out.println("--------------------------------------------------------------");
                            for (String s : text) System.out.println(s);
                            System.out.println("--------------------------------------------------------------");
                            System.out.println("Type the new content from here (type one line with only command <SAVE> or <END>):");
                            text = new ArrayList<>();
                            String actualLine = (scanner.nextLine());
                            while (!Objects.equals(actualLine, "<END>")) {
                                if (Objects.equals(actualLine, "<SAVE>")) {
                                    cp.writeDocument(input[1], input[2], text);
                                    System.out.println("Document saved.");
                                } else {
                                    text.add(actualLine);
                                }
                                actualLine = (scanner.nextLine());
                            }
                            cp.freeDocument(input[1], input[2]);
                            text.clear();
                            System.out.println("Document closed successfully.");
                        } else {
                            System.out.println("Error: This document does not exist.");
                        }
                    } else {
                        System.out.println("Error bad use of the command, use 'help [<command>' to get usage information.");
                    }
                    break;
                //endregion

                case "printDocument":
                    //region printDocument
                    if (input.length == 3) {
                        if (cp.documentExist(input[1], input[2])) {
                            ArrayList<String> text = cp.loadDocument(input[1], input[2]);
                            for (String s : text) System.out.println(s);
                            cp.freeDocument(input[1], input[2]);
                        } else System.out.println("Error: This document does not exist.");
                    } else
                        System.out.println("Error bad use of the command, use 'help [<command>' to get usage information.");
                    break;
                //endregion

                case "exportDocument":

                case "":
                    break;

                case "removeDocuments":
                    //region removeDocuments
                    if (input.length == 1) {
                        //authors_set.RemoveAuthor();
                        System.out.println("You have to specify the author and document name");
                    } else {
                        if (input.length % 2 == 1) {
                            for (int i = 1; i < input.length; i += 2) {
                                if (cp.authorExist(input[i])) {
                                    if (cp.documentExist(input[i], input[i + 1])) {
                                        if (cp.removeDocument(input[i], input[i + 1]))
                                            System.out.println("Document with author '" + input[i] + "' and title '" + input[i + 1] + "' have been properly erased.");
                                        else
                                            System.out.println("Unknown error. Document not erased");
                                    } else System.out.println("Error document does not exist.");
                                } else System.out.println("Error author does not exist.");
                            }
                        } else
                            System.out.println("Error bad use of the command, use 'help [<command>' to get usage information.");
                    }
                    break;
                //endregion

                case "findSimilar":
                    //region findSimilar
                    if (input.length == 4) {
                        if (cp.authorExist(input[1])) {
                            if (cp.documentExist(input[1], input[2])) {
                                try {
                                    int k = Integer.parseInt(input[3]);
                                    ArrayList<Pair<String, String>> result = cp.FindSimilar(input[1], input[2], k);
                                    for (Pair<String, String> p : result) {
                                        System.out.println("\t- " + p.getKey() + ' ' + p.getValue());
                                    }
                                    if (result.size() == 0) System.out.println("Not similar documents found.");

                                } catch (Exception e) {
                                    System.out.println(input[3] + " is not a number.");
                                }
                            } else System.out.println("Error document does not exist.");
                        } else System.out.println("Error author does not exist.");
                    } else
                        System.out.println("Error bad use of the command, use 'help [<command>' to get usage information.");
                    break;
                //endregion

                case "newBooleanExpression":
                    //region newBooleanExpression
                    aux = new StringBuilder();
                    for (int i = 1; i < input.length; ++i) {
                        aux.append(input[i]).append(" ");
                    }
                    int i = cp.addBooleanExpression(aux.toString());
                    if(i < 0) System.out.println("Error bad formulation.");
                    else System.out.println("New boolean expression added with id " + i);

                    break;
                //endregion

                case "listBooleanExpression":
                    //region listBooleanExpression
                    ArrayList<Pair<Integer, String>> tmp = cp.listBooleanExpression();
                    if (tmp.size() > 0) {

                        for (Pair<Integer, String> p : tmp) {
                            System.out.println("\t-" + p.getKey() + " -> " + p.getValue());
                        }
                    } else System.out.println("Theres not exist any boolean expression.");

                    break;
                //endregion

                case "removeBooleanExpression":
                    //region removeBooleanExpression
                    if (input.length == 2) {
                        try {
                            int k = Integer.parseInt(input[1]);
                            cp.removeBooleanExpression(k);
                            System.out.println("Boolean expression removed.");

                        } catch (Exception e) {
                            System.out.println(input[1] + " is not a number.");
                        }
                    } else
                        System.out.println("Error bad use of the command, use 'help [<command>' to get usage information.");

                    break;
                //endregion


                case "advancedSearch":
                    //region advancedSearch
                    if (input.length == 2) {
                        try {
                            int k = Integer.parseInt(input[1]);
                            ArrayList<Pair<String, String>> result = cp.findDocumentExpBool(k);
                            if (result.size() > 0) {
                                System.out.println("The boolean expression -->" + aux + "<--, can be found in this documents:");
                                for (Pair<String, String> d : result) {
                                    System.out.println("\t- " + d.getKey() + ' ' + d.getValue());
                                }
                            } else
                                System.out.println("The boolean expression " + aux + ", is not founded in any document");

                        } catch (Exception e) {
                            System.out.println(input[1] + " is not a number.");
                        }
                    } else
                        System.out.println("Error bad use of the command, use 'help [<command>' to get usage information.");
                    break;

                default:
                    System.out.println(input[0] + ": command not found");
            }
            input = (scanner.nextLine()).split(" ");
            input[0] = formCmd(input[0]);
        }
        cp.exit();
    }
}