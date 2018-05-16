package com.allen.ref;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ReferenceTest {

	static class MyObject{
		public String getName(){
			return this.toString();
		}
		
	}
	
	public static void main(String[] args) {
		SoftReference<MyObject> sr = new SoftReference<MyObject>(new MyObject());
		System.out.println("SoftReference before GC: " + sr.get().getName());
		System.gc();
		System.out.println("SoftReference after GC: " + sr.get() == null?"null" : sr.get().getName());

		MyObject o = new MyObject();
		WeakReference<MyObject> wr = new WeakReference<MyObject>(o);
		System.out.println("WeakReference before GC: " + wr.get().getName());
		System.gc();
		System.out.println("WeakReference after GC: " + wr.get().getName());
		o = null;
		System.gc();
		String msg = wr.get() == null?"null" : wr.get().getName();
		System.out.println("WeakReference after GC: " + msg);

		PhantomReference<MyObject> ex = new PhantomReference<MyObject>(
				new MyObject(), null);

		MyObject savePoint = new MyObject(); // a strong object

		ReferenceQueue<MyObject> savepointQ = new ReferenceQueue<MyObject>();// the
																				// ReferenceQueue
		WeakReference<MyObject> savePointWRefernce = new WeakReference<MyObject>(
				savePoint, savepointQ);

		System.out.println("SavePoint created as a weak ref "
				+ savePointWRefernce);
		Runtime.getRuntime().gc();
		System.out.println("Any weak references in Q ? "
				+ (savepointQ.poll() != null));
		savePoint = null; // the only strong reference has been removed. The
							// heap
							// object is now only weakly reachable

		System.out.println("Now to call gc...");
		Runtime.getRuntime().gc(); // the object will be cleared here - finalize
									// will be called.
		System.out
		.println("Does the weak reference still hold the heap object ? "
				+ (savePointWRefernce.get() != null));
System.out.println("Is the weak reference added to the ReferenceQ  ? "
		+ (savePointWRefernce.isEnqueued()));
		try {
			Reference<? extends MyObject> rr = savepointQ.remove();
			System.out.println("Any weak references in Q ? "
					+ (rr != null));
			System.out.println(rr.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out
				.println("Does the weak reference still hold the heap object ? "
						+ (savePointWRefernce.get() != null));
		System.out.println("Is the weak reference added to the ReferenceQ  ? "
				+ (savePointWRefernce.isEnqueued()));

	}
	
}
