import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hming on 3/22/18.
 */
public class Test {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("/Users/hming/Documents/NLP/Assignment6/CONLL_NAME_CORPUS_FOR_STUDENTS/CONLL_train.pos-chunk-name");
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String cur = "";
        List<String[]> sentence = new ArrayList<>();
        while ((cur = br.readLine()) != null) {
            if (cur.length() == 0) {
                if (sentence.size() != 0) {
                    boolean toPrint = false;
                    for (int i = 0; i < sentence.size(); i++) {
                        String[] curToken = sentence.get(i);
                        String word = curToken[0];
                        String nameTag = curToken[3];
                        if (i != 0 && Character.isUpperCase(word.charAt(0)) && !nameTag.startsWith("I-")) {
                            System.out.println(Arrays.toString(curToken));
                            toPrint = true;
                            break;
                        }
                    }
//                    if (toPrint) {
//                        System.out.println("--------Whole sentence--------");
//                        for (String[] a : sentence) {
//                            System.out.println(Arrays.toString(a));
//                        }
//                        System.out.println("----Sentence ends----");
//                    }
                    sentence.clear();
                }
            } else {
                String[] curArr = cur.split("\\t");
                sentence.add(curArr);
            }
        }
        br.close();
    }
}
