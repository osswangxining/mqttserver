package iot.mqtt.handler.entity;

import io.netty.util.concurrent.ScheduledFuture;
import iot.mqtt.message.Message;

public class HttpChannelEntity extends ChannelEntity {

	private String sessionId;
	private ScheduledFuture<?> scheduledTask = null;

	protected HttpChannelEntity() {
	}

	public HttpChannelEntity(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public ScheduledFuture<?> getScheduleTask() {
		return scheduledTask;
	}

	public void setScheduleTask(ScheduledFuture<?> scheduledTask) {
		this.scheduledTask = scheduledTask;
	}

	@Override
	public void write(Message message) {
	}

	@Override
	public int hashCode() {
		return getSessionId().hashCode();
	}

	@Override
	public String toString() {
		return "JSESSIONID=" + getSessionId();
	}
	
	public boolean isBlank(){
		return true;
	}
	
	public void setBlank(boolean isInit) {
	}
}
