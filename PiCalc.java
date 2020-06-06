import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;
public class PiCalc{
	public static void main (String[] args){
		Scanner s=new Scanner(System.in);
		while(true){
			System.out.println("Method to calculate pi:\n1. Nilakantha\n2. Chudnovsky\n3. Exit");
			int method=s.nextInt();
			s.nextLine();
			if(method==3){
				break;
			}
			System.out.println("Number of digits(not guaranteed to be correct):");
			int digits=s.nextInt();
			s.nextLine();
			System.out.println("Input the seconds to run to start:");
			int seconds=s.nextInt();
			switch(method){
				case 1:
					nilakantha(seconds, digits);
					break;
				case 2:
					chudnovsky(seconds, digits);
					break;
				default:
					System.out.println("Invalid method");
					break;
			}
		}
	}
	public static void nilakantha(int seconds, int digits){
		MathContext accuracy=new MathContext(digits);
		BigDecimal pi=new BigDecimal("3", accuracy);
		BigDecimal addSubtractValue=BigDecimal.ZERO;
		long bottomFirst=2;
		boolean add=true;
		long startTime=System.currentTimeMillis();
		while(true){
			addSubtractValue=new BigDecimal(String.valueOf(bottomFirst*(bottomFirst+1)*(bottomFirst+2)));
			addSubtractValue=new BigDecimal("4").divide(addSubtractValue, accuracy);
			if(add){
				pi=pi.add(addSubtractValue);
			}else{
				pi=pi.subtract(addSubtractValue);
			}
			bottomFirst+=2;
			add=!add;
			System.out.println(pi);
			if(System.currentTimeMillis()-startTime>=seconds*1000){
				break;
			}
		}
	}
	public static void chudnovsky(int seconds, int digits){
		long k=0;
		BigDecimal piLoop=new BigDecimal("0").setScale(digits);
		long startTime=System.currentTimeMillis();
		MathContext accuracy=new MathContext(digits);
		while(true){
			BigDecimal firstTop=factorial(new BigDecimal("6", accuracy).multiply(new BigDecimal(String.valueOf(k)), accuracy));
			BigDecimal secondTop=new BigDecimal("545140134", accuracy).multiply(new BigDecimal(String.valueOf(k)), accuracy).add(new BigDecimal("13591409"));
			BigDecimal firstBottom=factorial(new BigDecimal("3", accuracy).multiply(new BigDecimal(String.valueOf(k)), accuracy));
			BigDecimal secondBottom=factorial(new BigDecimal(String.valueOf(k))).pow(3);
			BigDecimal thirdBottom=new BigDecimal("-262537412640768000", accuracy).pow((int)k);
			BigDecimal topValue=firstTop.multiply(secondTop, accuracy);
			BigDecimal bottomValue=firstBottom.multiply(secondBottom, accuracy).multiply(thirdBottom, accuracy);
			BigDecimal loopPi=topValue.divide(bottomValue, digits, RoundingMode.HALF_UP);
			piLoop=piLoop.add(loopPi);
			if(k!=0){
				System.out.println(new BigDecimal("426880").multiply(new BigDecimal("10005").sqrt(accuracy)).divide(piLoop, accuracy));
			}
			k++;
			if(System.currentTimeMillis()-startTime>=seconds*1000){
				break;
			}
		}
	}

	public static BigDecimal pow(BigDecimal num, BigDecimal pow){
		for(BigDecimal i=BigDecimal.ONE; i.compareTo(pow)==-1; i.add(BigDecimal.ONE)){
			num=num.multiply(num);
		}
		return num;
	}

	public static BigDecimal factorial(BigDecimal num){
		if(num.compareTo(BigDecimal.ZERO)==0){
			return BigDecimal.ONE;
		}
		BigDecimal factorial=num.add(BigDecimal.ZERO);
		for(BigDecimal i=num.subtract(BigDecimal.ONE); i.compareTo(BigDecimal.ZERO)==1; i=i.subtract(BigDecimal.ONE)){
			factorial=factorial.multiply(i, new MathContext(300));
		}
		return factorial;
	}
}

