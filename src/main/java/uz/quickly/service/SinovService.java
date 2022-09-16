package uz.quickly.service;

import org.springframework.stereotype.Service;
import uz.quickly.domain.Text;
import uz.quickly.repository.TextRepo;

import java.util.Optional;

@Service
public class SinovService {
    final TextRepo repoText;

    public SinovService(TextRepo repoText) {
        this.repoText = repoText;
    }

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
