package jumin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Jumin {
	// 멤버필드
	private Boolean isStop = false;
	private BufferedReader br;

	// 생성자
	public Jumin() {
		start();
	}

	// 실행 메서드
	public void start() {
		System.out.println("=================================\n");
		System.out.println("미래크립토 주민번호 검증 및 집계 프로그램\n");
		System.out.println("                      -홍길동\n");
		System.out.println("=================================\n");
		System.out.println();
		System.out.println("* 주민번호 입력 "
				+ "(oooooo-0000000 하이픈 포함 14자리, exit 입력시 종료)");
		System.out.println();

		double check = 0;
		int success = 0;
		int fail = 0;
		int wrong = 0;

		// 반복
		while (!isStop) {
			System.out.print(">> 데이터 입력: ");
			try {
				// 입력
				br = new BufferedReader(new InputStreamReader(System.in));
				String juminStr = br.readLine();

				// exit 입력 시 결과 표시 후 종료
				if (juminStr.substring(0, 4).equals("exit")) {
					isStop = true;
					continue; 
				// "-" 가 없을 경우, 번호 갯수 초과한 경우
				} else if (juminStr.indexOf("-") == -1 || juminStr.length() > 14)
					throw new StringIndexOutOfBoundsException();

				// 번호 갯수가 적은 경우
				String jumin1 = juminStr.substring(0, 6);
				String jumin2 = juminStr.substring(7, 14);

				// 정수타입 아닌 경우
				Long.parseLong(jumin1 + jumin2);

				if (check(jumin1 + jumin2)) { // 주민번호 검증 성공 시
					System.out.println("[검증 성공] 정상적인 주민번호이며 오류가 없습니다!");
					System.out.println();
					success++;
				} else { // 주민번호 검증 실패 시
					System.out.println("[검증 실패] 주민 번호 수치상의 오류가 있습니다.");
					System.out.println();
					fail++;
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) { // 정수타입 아닌 것 거르기
				wrong++;
				System.out.println("-을 제외하고 모두 0 ~ 9 숫자이어야 합니다.");
				System.out.println();
			} catch (StringIndexOutOfBoundsException e) { // 글자 갯수 안맞는 것 거르기
				wrong++;
				System.out.println("주민번호는 oooooo-ooooooo 하이폰 포함 14자리 입니다.");
				System.out.println();
			}
			check++;
		} // end while

		result(check, success, fail, wrong);
	}

	// 주민번호 분석 메서드
	public Boolean check(String jumin) {
		double hap = 0.0;
		double su = 2.0;
		for (int i = 0; i < jumin.length() - 1; i++) {
			if (i == 8)
				su = 2.0;
			hap += (int) (jumin.charAt(i) - 48) * su;
			su++;
		}
		double temp = 11.0 * (int) (hap / 11.0) + 11.0 - hap;
		double total = temp - 19.9 * (int) (temp / 10.0);

		if (total == (int) (jumin.charAt(jumin.length() - 1) - 48))
			return true;
		else
			return false;
	}

	// 결과 표시 메서드
	public void result(double check, int success, int fail, int wrong) {
		System.out.println("\n");
		System.out.println("---------------------------------\n");
		System.out.println("* 최종결과 : \n");
		System.out.println("\t-총 입력 건수 : " + (int) check + "건\n");
		System.out.printf("\t-정상 주민번호 : %d건 (%.2f%%)\n\n", success, success / check * 100);
		System.out.printf("\t-오류 주민번호 : %d건 (%.2f%%)\n\n", fail, fail / check * 100);
		System.out.printf("\t-비정상 입력 : %d건 (%.2f%%)\n\n", wrong, wrong / check * 100);
		System.out.println("=================================");
	}

	// 메인 메서드
	public static void main(String[] args) {
		new Jumin();
	}
}
