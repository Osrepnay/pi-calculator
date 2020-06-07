import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;
public class PiCalc{
	public static void main (String[] args){
		Scanner s=new Scanner(System.in);
		String[] piMethodsPrint=new String[]{"Chudnovsky", "Nilakantha", "Leibniz", "Wallis", "Zeta(2)"};
		String[] piMethods=new String[]{"chudnovsky", "nilakantha", "leibniz", "wallis", "zeta2"};
		while(true){
			System.out.println("Method to calculate pi:");
			for(int i=0; i<piMethodsPrint.length; i++){
				System.out.println((i+1)+". "+piMethodsPrint[i]);
			}
			System.out.println((piMethods.length+1)+". Exit");
			int method=s.nextInt();
			s.nextLine();
			if(method==piMethods.length+1){
				break;
			}
			System.out.println("Number of digits(not guaranteed to be correct):");
			int digits=s.nextInt();
			s.nextLine();
			System.out.println("Input the seconds to run to start:");
			int seconds=s.nextInt();
			try{
			Method piMethod=new PiCalc().getClass().getDeclaredMethod(piMethods[method-1], int.class, int.class);
			piMethod.invoke(new PiCalc(), seconds, digits);
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	public static void chudnovsky(int seconds, int digits){
		MathContext accuracy=new MathContext(digits);
		BigDecimal piLoop=new BigDecimal("0").setScale(digits);
		long k=0;
		long startTime=System.currentTimeMillis();
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

	public static void leibniz(int seconds, int digits){
		MathContext accuracy=new MathContext(digits);
		BigDecimal pi=new BigDecimal("1", accuracy);
		BigDecimal bottomValue=new BigDecimal("3", accuracy);
		boolean add=false;
		long startTime=System.currentTimeMillis();
		while(true){
			if(add){
				pi=pi.add(BigDecimal.ONE.divide(bottomValue, accuracy));
			}else{
				pi=pi.subtract(BigDecimal.ONE.divide(bottomValue, accuracy));
			}
			System.out.println(pi.multiply(new BigDecimal("4")));
			add=!add;
			bottomValue=bottomValue.add(new BigDecimal("2"));
			if(System.currentTimeMillis()-startTime>=seconds*1000){
				break;
			}
		}
	}

	public static void wallis(int seconds, int digits){
		MathContext accuracy=new MathContext(digits);
		BigDecimal pi=new BigDecimal("2", accuracy);
		BigDecimal topValue=new BigDecimal("2", accuracy);
		BigDecimal bottomValue=new BigDecimal("3", accuracy);
		long loopCount=0;
		long startTime=System.currentTimeMillis();
		while(true){
			pi=pi.multiply(topValue.divide(bottomValue, accuracy), accuracy);
			System.out.println(pi.multiply(new BigDecimal("2"), accuracy));
			if((loopCount%2)==0){
				topValue=topValue.add(new BigDecimal("2"));
			}else{
				bottomValue=bottomValue.add(new BigDecimal("2"));
			}
			loopCount++;
			if(System.currentTimeMillis()-startTime>=seconds*1000){
				break;
			}
		}
	}

	public static void zeta2(int seconds, int digits){
		MathContext accuracy=new MathContext(digits);
		BigDecimal pi=new BigDecimal("0", accuracy);
		long loopCount=1;
		long startTime=System.currentTimeMillis();
		while(true){
			pi=pi.add(BigDecimal.ONE.divide((new BigDecimal(String.valueOf(loopCount)).pow(2)), accuracy));
			System.out.println(pi.multiply(new BigDecimal("6")).sqrt(accuracy));
			loopCount++;
			if(System.currentTimeMillis()-startTime>=seconds*1000){
				break;
			}
		}
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

