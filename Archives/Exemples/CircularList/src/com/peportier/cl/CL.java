package com.peportier.cl;
import java.util.Arrays;
import com.google.java.contract.Requires;
import com.google.java.contract.Ensures;
import com.google.java.contract.Invariant;

/**
 * 
 * @author peportier
 *
 */
@Invariant("s>=0")
public class CL {
	/**
	 * s: size of the circular list (CL)
	 */
	public final int s;
	/**
	 * a: array used to model a CL
	 */
	private int a[];
	
	CL(int _size){
		s = _size;
		a = new int[s];
	}
	
	CL(int _size, int[] _vals) {
		s = _size;
		a = _vals;
	}
	
	/**
	 * set the value of an element of the CL
	 * 
	 * @param _i index in CL understood modulo the size of the CL
	 * @param _x value of the new element
	 */
	@Requires("_i >= 0")
	public void set(int _i, int _x){
		a[(_i%s)] = _x;
	}
	
	/**
	 * set all the values of CL from array arg v
	 * @param _v array of values
	 */
	@Requires("_v.length == s")
	public void set_batch(int[] _v){
		a = _v;
	}
	
	/**
	 * get a value from the CL
	 * 
	 * @param _i index in CL understood modulo the size of the CL
	 * @return the value at index _i modulo the size of the CL
	 */
	@Requires("_i >= 0")
	public int get(int _i){
		return a[(_i%s)];
	}
	
	public int[] get_copy_arr() {
		int res[] = a.clone();
		return res;
	}
	
	/**
	 * rotate a CL by an offset of _i
	 * 
	 * @param _i
	 * @return an array that represents the rotation of the CL
	 */
	@Requires("_i >= 0")
	public int[] rot(int _i){
		int[] res = new int[s];
		for(int j=_i, k=0 ; j < (_i+s) ; j++, k++){
			res[k] = get(j);
		}
		return res;
	}
	
	/**
	 * @param _a CL
	 * @param _i offset used to rotate _a
	 * @param _b CL
	 * @param _j offset used to rotate _b
	 * @return Is the _ith-rotation of CL _a equals to the _jth-rotation of CL _b?
	 */
	@Requires({"_i >= 0", "_j >= 0"})
	public static boolean rot_eq(CL _a, int _i, CL _b, int _j){
		return Arrays.equals(_a.rot(_i), _b.rot(_j));
	}

	/**
	 * 
	 * @param _a a CL
	 * @param _b a CL
	 * @return Are the lexicographically sorted versions of a and b are equal?
	 */
	@Requires("_a.s == _b.s")
	public static boolean eq(CL _a, CL _b) {
		for(int i=0 ; i<_a.s ; i++){
			if(Arrays.equals(_a.a, _b.rot(i))) return true;
		}
		return false;
	}
	
	@Requires({"_h >= 0", "_i >= 0", "_j >= 0", "_a.s == _b.s"})
	public static boolean inv_P(int _h, int _i, int _j, CL _a, CL _b) {
		boolean res = true;
		for (int k = 0 ; k < _h ; k++) 
			res &= _a.rot(_i)[k] == _b.rot(_j)[k];
		return res;
	}
	
	public int[] smallest() {
		int aa[] = a.clone();
		for (int k = 1 ; k < s ; k++) {
			int rot_k[] = rot(k);
			boolean new_smallest = false;
			for (int i = 0 ; i < s ; i++) {
				if        (aa[i] == rot_k[i]) {
					// do nothing
				} else if (aa[i] > rot_k[i]) {
					new_smallest = true; break;
				} else if (aa[i] < rot_k[i]) {
					new_smallest = false; break;
				} else assert false;
			}
			if (new_smallest) aa = rot_k.clone();
		}
		return aa;
	}
	
	@Requires({"_i >= 0", "_a.s == _b.s"})
	public static boolean inv_Q(int _i, CL _a, CL _b) {
		boolean res = true;
		int bb[] = _b.smallest();
		for (int k = 0 ; k < _i ; k++) {
			int rot_k_of_a[] = _a.rot(k);
			for (int j = 0 ; j < _a.s ; j++) {
				if (rot_k_of_a[j] == bb[j]) {
					if (j == (_a.s - 1)) { res = false; break; }
				} else {
					if (rot_k_of_a[j] > bb[j]) {res &= true; break; }
					else { res = false; break; }
				}
			}
			if (res == false) break;
		}
		return res;
	}
	
	@Requires("_a.s == _b.s")
	@Ensures("result == CL.eq(_a,_b)")
	public static boolean eq2(CL _a, CL _b) {
		boolean res = false;
		int h,i,j;
		h = i = j = 0;
		assert CL.inv_P(h, i, j, _a, _b);
		assert CL.inv_Q(i, _a, _b);
		assert CL.inv_Q(j, _b, _a);
		int progress;
		while (h<_a.s && i<_a.s && j<_a.s) {
			assert (h+i+j) <= (3*_a.s - 3);
			progress = h+i+j;
			// increase h+i+j while maintaining P & QA & QB
			if      (_a.get(i+h) == _b.get(j+h)) h = h+1;
			else if (_a.get(i+h) > _b.get(j+h)) {i = i+h+1; h=0;}
			else if (_b.get(j+h) > _a.get(i+h)) {j = j+h+1; h=0;}
			else assert false;
			assert CL.inv_P(h, i, j, _a, _b);
			assert CL.inv_Q(i, _a, _b);
			assert CL.inv_Q(j, _b, _a);
			assert h+i+j > progress;
		}
		assert CL.inv_P(h, i, j, _a, _b);
		assert CL.inv_Q(i, _a, _b);
		assert CL.inv_Q(j, _b, _a);
		assert (h>=_a.s) || (i>=_a.s) || (j>=_a.s);
		if (h >= _a.s) res = true;
		else if (i>=_a.s || j>=_a.s) res = false;
		return res;
	}
}
