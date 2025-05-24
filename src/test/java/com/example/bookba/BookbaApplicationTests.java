import com.example.bookba.BookbaApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = BookbaApplication.class) // Явно укажите главный класс
@ActiveProfiles("test")
public class BookbaApplicationTests {

	@Test
	void contextLoads() {
		// Пустой тест для проверки загрузки контекста
	}
}