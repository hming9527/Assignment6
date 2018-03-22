import java.io.*;
import java.util.*;

/**
 * The FeatureBuilder will be applied to .pos-chunk-name and .pos-chunk files to produce feature-enhanced files.
 */
public class FeatureBuilder {

    private static Set<String> excluded = new HashSet<>();

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println ("FeatureBuilder requires at least one argument:  inputFile");
            System.exit(1);
        }
        for (String f : args) {
            generateFeatures(f);
        }
        String[] arr = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December",
        "Jan", "Feb", "Mar", "Apr", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
        "Sunday", "Mon", "Tues", "Wed", "Thurs", "Fri", "Sat", "Sun", "I"};
        for (String s : arr) excluded.add(s);
    }

    public static void generateFeatures(String input) throws IOException {
        boolean tagIncluded = input.endsWith("name") ? true : false;
        File inputFile = new File(input);
        String output = input + "-features";
        File outputFile = new File(output);
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
        String cur = "";
        List<String[]> sentence = new ArrayList<>();
        while ((cur = br.readLine()) != null) {
            if (cur.length() == 0) {
                if (sentence.size() != 0) {
                    for (int i = 0; i < sentence.size(); i++) {
                        String[] prevToken = ((i - 1) >= 0) ? sentence.get(i - 1) : new String[]{"@@"};
                        String[] curToken = sentence.get(i);
                        String[] nextToken = ((i + 1) < sentence.size()) ? sentence.get(i + 1) : new String[]{"@@"};
                        bw.write(curToken[0] +
                                "\tprevToken=" + prevToken[0] +
                                "\tcurToken=" + curToken[0] +
                                "\tnextToken=" + nextToken[0] +
                                "\tcurPOS=" + curToken[1] +
                                "\tnextPOS=" + (nextToken.length >=3 ? nextToken[1] : "@@") +
                                "\tprevChunkTag=" + (prevToken.length >= 3 ? prevToken[2] : "@@") +
                                "\tcurChunkTag=" + curToken[2] +
                                "\tnextChunkTag=" + (nextToken.length >= 3 ? nextToken[2] : "@@") +
                                "\tprevTokenChunkTagNameTag=" + prevToken[0] + "^" + (prevToken.length >= 3 ? prevToken[2] : "@@") + "^" + (prevToken.length == 4 ? prevToken[3] : "@@") +
                                "\tcurTokenPOSChunkTag=" + curToken[0] + "^" + curToken[1] + "^" + curToken[2] + "^" + (curToken[1].startsWith("NN") || curToken[1].startsWith("JJ")) +
                                "\tnextTokenPOSChunkTag=" + nextToken[0] + "^" + (nextToken.length >=3 ? nextToken[1] : "@@") + "^" + (nextToken.length >= 3 ? nextToken[2] : "@@") +
                                "\tcapitalInitial=" + Character.isUpperCase(curToken[0].charAt(0)) +
                                "\tisFirstWord=" + (i == 0) +
                                "\tallCapital=" + isAllCapital(curToken[0]) +
                                "\tconjunction1=" + Character.isUpperCase(curToken[0].charAt(0)) + "^" + (i == 0) + "^" + isAllCapital(curToken[0]) + "^" + excluded.contains(curToken[0]) + "^" + (prevToken.length == 4 ? prevToken[3] : "@@") +
                                "\tcontainsNumOrHyphenOrDot=" + curToken[0].matches(".*[0-9,-,\\.]+.*") +
                                "\texclusionList=" + excluded.contains(curToken[0]) +
                                "\tcurNNorJJ=" + (curToken[1].startsWith("NN") || curToken[1].startsWith("JJ")) +
                                "\tprevNameTag=" + (prevToken.length == 4 ? prevToken[3] : "@@"));
                        if (tagIncluded) {
                            bw.write("\t" + curToken[curToken.length - 1]);
                        } else {
                            bw.write("\t@@");
                        }
                        bw.newLine();
                    }
                    sentence.clear();
                    bw.newLine();
                }
            } else {
                String[] curArr = cur.split("\\t");
                sentence.add(curArr);
            }
        }
        br.close();
        bw.close();
    }

    public static boolean isAllCapital(String s) {
        char[] c = s.toCharArray();
        for (Character ch : c) {
            if (Character.isLowerCase(ch)) return false;
        }
        return true;
    }
}
