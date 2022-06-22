package uz.quicklyWriteHtml.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.quicklyWriteHtml.entitiy.Text;

public interface TextRepo extends JpaRepository<Text, Integer> {
    @Query(
            value = "SELECT COUNT(text) FROM texts",
            nativeQuery = true)
    Integer countOfTexts();

}
