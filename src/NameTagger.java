import java.io.*;

/**
 * Created by hming on 3/22/18.
 */
public class NameTagger {

    public static void main(String[] args) throws IOException {
        String train = "/Users/hming/Documents/NLP/Assignment6/CONLL_NAME_CORPUS_FOR_STUDENTS/CONLL_train.pos-chunk-name";
        String dev = "/Users/hming/Documents/NLP/Assignment6/CONLL_NAME_CORPUS_FOR_STUDENTS/CONLL_dev.pos-chunk";
        String test = "/Users/hming/Documents/NLP/Assignment6/CONLL_NAME_CORPUS_FOR_STUDENTS/CONLL_test.pos-chunk";

        FeatureBuilder.main(new String[]{train, dev});

        String train_with_features = train + "-features";
        String train_model = train_with_features + "-model";
        MEtrain.main(new String[]{train_with_features, train_model});

        String dev_with_features = dev + "-features";
        String dev_prediction = "/Users/hming/Documents/NLP/Assignment6/CONLL_NAME_CORPUS_FOR_STUDENTS/dev_response.name";
        MEtag.main(new String[]{dev_with_features, train_model, dev_prediction});

        System.out.println("-------Message from scorer-------");

        ProcessBuilder pb = new ProcessBuilder("python", "/Users/hming/Documents/NLP/Assignment6/src/scorer.py");
        Process p = pb.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
    }
}
