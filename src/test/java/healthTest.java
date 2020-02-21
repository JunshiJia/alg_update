import com.junshijia.Alg.health.SendHealth;
import org.junit.Test;

public class healthTest {
    @Test
    public void healthTest(){
        SendHealth s = new SendHealth();
        s.sendAllHealth();
    }
}
