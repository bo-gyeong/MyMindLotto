import java.util.Scanner;
public class quadrangle {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Input values of (x1, y1) (x2, y2) : ");
		int x1 = sc.nextInt();
		int y1 = sc.nextInt();
		int x2 = sc.nextInt();
		int y2 = sc.nextInt();
		System.out.print("Input values of (x, y) ? ");
		int x = sc.nextInt();
		int y = sc.nextInt();
		
		if ((x1 >= x)&&(y1 >= y)&&(x2 <= x)&&(y2 <= y))
			System.out.println("IN the rectangle!!");
		else if ((x1 <= x)&&(y1 <= y)&&(x2 >= x)&&(y2 >= y))
			System.out.println("IN the rectangle!!");
		else if ((x1 <= x)&&(y1 >= y)&&(x2 >= x)&&(y2 <= y))
			System.out.println("IN the rectangle!!");
		else if ((x1 >= x)&&(y1 <= y)&&(x2 <= x)&&(y2 >= y))
			System.out.println("IN the rectangle!!");
		else
			System.out.println("OUT of the rectangle!!");
		
		sc.close();

	}

}
