package model;

public class Invitation {

	private int invitationId;
	private int eventId;
	private int userId;
	private String invitationStatus;
	private int invitationRole;
	
	public Invitation(int invitationId, int eventId, int userId, String invitationStatus, int invitationRole) {
		this.invitationId = invitationId;
		this.eventId = eventId;
		this.userId = userId;
		this.invitationStatus = invitationStatus;
		this.invitationRole = invitationRole;
	}

	public int getInvitationId() {
		return invitationId;
	}

	public int getEventId() {
		return eventId;
	}

	public int getUserId() {
		return userId;
	}

	public String getInvitationStatus() {
		return invitationStatus;
	}

	public int getInvitationRole() {
		return invitationRole;
	}

	public void setInvitationStatus(String invitationStatus) {
		this.invitationStatus = invitationStatus;
	}
	
}
