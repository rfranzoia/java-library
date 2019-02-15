import java.util.Random;

public class TestCodeGenerator {

	public static final String REGEX_LONG = "([A-Fa-f0-9]{4})([A-Fa-f0-9]{4})([A-Fa-f0-9]{4})([A-Fa-f0-9]{0,4})";
	public static final String REGEX_INTEGER = "([A-Fa-f0-9]{2})([A-Fa-f0-9]{4})([A-Fa-f0-9]{0,2})";
	
	public static void main(String[] args) {
		
		do {
			try {
				System.out.println(getLongCode());
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (true);
		
	}
	
	public static String getLongCode() {
		Random r = new Random(System.currentTimeMillis());
		Long n = r.nextLong();
		return String.format("%016X", n).replaceAll(REGEX_LONG, "$1.$2.$3.$4");
	}
	
	public static String getIntegerCode() {
		Random r = new Random(System.currentTimeMillis());
		Integer n = r.nextInt();
		return String.format("%08X", n).replaceAll(REGEX_INTEGER, "$1.$2.$3");
	}
}
