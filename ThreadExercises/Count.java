package ThreadExercises;

class countThread extends Thread{
	public void run(){
		try{
			int i=0;
			while(i<Integer.MAX_VALUE){
				sleep(1000);
				i++;
				System.out.println(i);
			}
			
		}
		catch(InterruptedException e){return;}
		
		
	}
}
public class Count {

	public static void main(String[] args) {
		countThread th = new countThread();
		th.start();
		try{
			th.join();
		}
		catch(InterruptedException e){
			return;
		}

	}

}
