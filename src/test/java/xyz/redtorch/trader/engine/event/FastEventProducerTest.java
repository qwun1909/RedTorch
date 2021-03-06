package xyz.redtorch.trader.engine.event;

import com.lmax.disruptor.RingBuffer;

import xyz.redtorch.trader.engine.event.FastEvent;

public class FastEventProducerTest extends FastEventProducerAbstract {
	public FastEventProducerTest(RingBuffer<FastEvent> ringBuffer) {
		super(ringBuffer);
	}

	public void onData(String eventStr) {
		long sequence = ringBuffer.next(); // Grab the next sequence
		try {
			FastEvent fastEvent = ringBuffer.get(sequence); // Get the entry in the Disruptor for the sequence
			fastEvent.setEvent(eventStr+System.currentTimeMillis());
		} finally {
			ringBuffer.publish(sequence);
		}
	}
}