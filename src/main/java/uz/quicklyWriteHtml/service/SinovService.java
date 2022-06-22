package uz.quicklyWriteHtml.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.quicklyWriteHtml.entitiy.Text;
import uz.quicklyWriteHtml.repository.TextRepo;

import java.util.Optional;

@Service
public class SinovService {
    @Autowired
    TextRepo repoText;

    public Text findByRandomId() {
        Integer countOfRows = repoText.countOfTexts();
        int randId = (int) ((Math.random() * (countOfRows - 1)) + 1);
        Optional<Text> optionalText = repoText.findById(randId);
        while (optionalText.isEmpty()) {
            randId = (int) ((Math.random() * (countOfRows - 1)) + 1);
            optionalText = repoText.findById(randId);
        }
        Text text = optionalText.get();
        text.setText(text.getText().replace("‘", "'"));
        text.setText(text.getText().replace("’", "'"));

        return text;
    }
}
