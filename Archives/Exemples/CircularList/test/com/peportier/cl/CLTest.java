package com.peportier.cl;

import static org.junit.Assert.*;

import org.junit.Test;

public class CLTest {

	@Test
	public void testSetGet() {
		CL a = new CL(3);
		a.set(0, 7);
		assertEquals(7,a.get(0));
	}
	
	@Test
	public void testSetGetUnsetItem() {
		CL a = new CL(3);
		a.set(0, 7);
		assertEquals(0,a.get(1));
	}
	
	@Test
	public void testSetGetModulo() {
		CL a = new CL(3);
		a.set(0, 7); a.set(4, 8); a.set(2, 9);
		assertEquals(8,a.get(1));
	}
	
	@Test
	public void testSet_batch() {
		CL a = new CL(3);
		int[] expected = {8, 9, 7};
		a.set_batch(expected);
		int[] res = new int[3];
		for (int i=0 ; i < 3 ; i++) {
			res[i] = a.get(i);
		}
		assertArrayEquals(expected, res);
	}

	@Test
	public void testRot() {
		CL a = new CL(3);
		a.set(0, 7); a.set(1, 8); a.set(2, 9);
		int[] expected = {8, 9, 7};
		assertArrayEquals(expected, a.rot(1));
	}

	@Test
	public void testRot_eq() {
		CL a = new CL(3);
	    a.set(0,7); a.set(1,9); a.set(2,8);
	    CL b = new CL(3);
	    b.set(0,9); b.set(1,8); b.set(2,7);
	    assertTrue(CL.rot_eq(a, 0, b, 2));
	}
	
	@Test
	public void testCL_eq() {
		CL a = new CL(3);
	    a.set(0,7); a.set(1,9); a.set(2,8);
	    CL b = new CL(3);
	    b.set(0,9); b.set(1,8); b.set(2,7);
	    assertTrue(CL.eq(a, b));
	}
	
	@Test
	public void testCL_eq_false() {
		CL a = new CL(3);
	    a.set(0,7); a.set(1,9); a.set(2,8);
	    CL b = new CL(3);
	    b.set(0,9); b.set(1,8); b.set(2,8);
	    assertFalse(CL.eq(a, b));
	}
	
	@Test(timeout=1000)
	public void testCL_eq_scale() {
		int SIZE = 10000;
		int[] a_vals = new int[SIZE];
		for (int i = 0 ; i < SIZE ; i++) {
			a_vals[i] = i;
		}
		CL a = new CL(SIZE, a_vals);
		int[] b_vals = a.rot(SIZE/2);
		CL b = new CL(SIZE, b_vals);
	    assertTrue(CL.eq(a, b));
	}
	
	@Test
	public void testCL_inv_P() {
		CL a = new CL(3);
	    a.set(0,7); a.set(1,9); a.set(2,8);
	    CL b = new CL(3);
	    b.set(0,9); b.set(1,8); b.set(2,7);
	    assertTrue(CL.inv_P(2, 0, 2, a, b));
	}
	
	@Test
	public void testCL_smallest() {
		CL a = new CL(3);
	    a.set(0,9); a.set(1,8); a.set(2,8);
	    int[] expected = {8, 8, 9};
		assertArrayEquals(expected, a.smallest());
	}
	
	@Test
	public void testCL_inv_Q() {
		CL a = new CL(3);
	    a.set(0,7); a.set(1,9); a.set(2,8);
	    CL b = new CL(3);
	    b.set(0,9); b.set(1,8); b.set(2,8);
	    assertTrue(CL.inv_Q(2, b, a));
	}
	
	@Test()
	public void testCL_eq2_true() {
		CL a = new CL(3);
	    a.set(0,7); a.set(1,9); a.set(2,8);
	    CL b = new CL(3);
	    b.set(0,9); b.set(1,8); b.set(2,7);
	    assertTrue(CL.eq2(a, b));
	}
	
	@Test
	public void testCL_eq2_false() {
		CL a = new CL(3);
	    a.set(0,7); a.set(1,9); a.set(2,8);
	    CL b = new CL(3);
	    b.set(0,9); b.set(1,8); b.set(2,8);
	    assertFalse(CL.eq2(a, b));
	}
	
	@Test(timeout=1000)
	public void testCL_eq2_scale() {
		int SIZE = 500;
		int[] a_vals = new int[SIZE];
		for (int i = 0 ; i < SIZE ; i++) {
			a_vals[i] = i;
		}
		CL a = new CL(SIZE, a_vals);
		int[] b_vals = a.rot(SIZE/2);
		CL b = new CL(SIZE, b_vals);
	    assertTrue(CL.eq2(a, b));
	}

}
