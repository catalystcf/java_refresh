package mt.simplenotify;

/**
 * Problem: Two threads are running in parallel and utilize the same lock. One
 * thread enters a shared lock and enters a wait. The the other thread also
 * takes a hold of the lock, issues notify all and enters a long sleep. Analyze
 * the expected behavior of two threads. .
 * 
 * @see http://codereview.aaroshka.com/java-multi-threading-questions-on-waitnotify/
 * 
 * <pre>
 * 
 * {@code
 * //thread 1
 * 	synchronized(obj)
 *  {
 *      obj.wait();
 *      System.out.println("Here");
 *  }
 *  
 *  //Thread 2
 *  synchronized(obj)
 *  {
 * 		obj.notifyAll();
 *      Thread.sleep(30);
 *      System.out.println("There");
 *  
 *  }
 *  
 * 
 * }
 * </pre>
 * 
 * @author Alex Ar
 *
 */
public class NotifyDetails {

	Object obj = new Object() {
		@Override
		public String toString() {
			return "Shared Lock";
		}
	};

	public void illustrationOne() throws InterruptedException {

		class T1Thread extends Thread {

			@Override
			public void run() {
				synchronized (obj) {
					try {

						obj.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("After wait");
				}
			}

		}
		;

		final Thread t1 = new T1Thread();
		final Thread t2 = new T1Thread();
		final Thread t3 = new T1Thread();

		t1.setName("T1");
		t2.setName("T2");
		t3.setName("T3");

		final Thread tControl = new Thread() {

			@Override
			public void run() {
				try {
				while (true)
				{
					synchronized (obj) {

						
							Thread.sleep(1000); // 1 second
							obj.notify();
							Thread.sleep(1000 * 10) ; // 200 seconds
							System.out.println("After Sleep");
					};
					
					Thread.sleep(1);
				}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
			}

		};

		tControl.setName("T-control");

		Thread tObserver = new Thread("T-Observer") {
			public void run() {
				long initial_time = System.currentTimeMillis();
				while(true)
				{
					
					
					System.out.printf("%s - T1=%s, T2=%s, T3=%s\n", System.currentTimeMillis() - initial_time, 
								t1.getState(), t2.getState(), t3.getState()
							);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		};
		
		tObserver.start();
		Thread.sleep(500);
		
		t1.start();
		t2.start();

		Thread.sleep(1000);

		tControl.start();

		Thread.sleep(2000);
		t3.start();

	};

	public static void main(String[] args) throws InterruptedException {
		NotifyDetails nd = new NotifyDetails();
		nd.illustrationOne();
	}
}
