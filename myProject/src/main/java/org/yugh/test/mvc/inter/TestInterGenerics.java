package org.yugh.test.mvc.inter;

public interface TestInterGenerics<O,G> {

	public G find(O object) throws Exception;
}
