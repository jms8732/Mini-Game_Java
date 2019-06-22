import java.util.Scanner;

public class test1 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the data : " );
        int date = sc.nextInt();
        assert(date >= 1 && date <= 31 ) : "Wrong data  :" + date;
        System.out.printf("date = %d\n",date);
    }
}
