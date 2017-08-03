package com.amrutsoftware.plugin.listener;

import java.util.Map;

import javax.inject.Inject;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.event.JiraEvent;
import com.atlassian.jira.event.issue.AbstractIssueEventListener;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.event.issue.IssueEventListener;
import com.atlassian.jira.event.type.EventType;
import com.atlassian.jira.event.user.UserEvent;
import com.atlassian.jira.event.user.UserEventListener;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.comments.CommentManager;
import com.atlassian.jira.issue.link.IssueLink;
import com.atlassian.jira.issue.link.IssueLinkManager;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.apache.log4j.Logger;

public class CopyComment extends AbstractIssueEventListener
implements IssueEventListener, UserEventListener
{

	 /*@ComponentImport
	 private final EventPublisher eventPublisher;*/
	
	 public void init(Map params) {}
	 
	 @Inject
	 public CopyComment(EventPublisher eventPublisher) {
		 eventPublisher.register(this);
  // Demonstration only -- don't do this in real code!
	 }
	 
	 public String[] getAcceptedParams()
	  {
	    return new String[0];
	  }
	  public void issueAssigned(IssueEvent event) {}
	  
	  public void issueClosed(IssueEvent event) {}
	  
	  @EventListener
	  public void issueCreated(IssueEvent event)
	  {
	  }
	  
	  @EventListener
	    public void onIssueEvent(IssueEvent issueEvent) {
	        Long eventTypeId = issueEvent.getEventTypeId();
	        Issue issue = issueEvent.getIssue();
	        // if it's an event we're interested in, log it
	        if (eventTypeId.equals(EventType.ISSUE_CREATED_ID)) {
	            
	        } else if (eventTypeId.equals(EventType.ISSUE_RESOLVED_ID)) {
	            
	        } else if (eventTypeId.equals(EventType.ISSUE_COMMENTED_ID)) {
	        	UpdateLinkedIssueComment(issueEvent);
	        }
	    }
      public void issueResolved(IssueEvent event) {}
	  
	  public void issueReopened(IssueEvent event) {}
	  
	  public void issueUpdated(IssueEvent event) {}
	  
	  @EventListener
	  public void issueCommented(IssueEvent event)
	  {
	  }
	  
	 
	  private void UpdateLinkedIssueComment(JiraEvent event)
	  {
		 try
	    {
	      IssueEvent issueEvent;
	      if ((event instanceof IssueEvent))
	      {
	        issueEvent = (IssueEvent)event;
	        if (!issueEvent.getComment().getBody().startsWith("Comment copied from Linked Issue :"))
	        {
	          String linkType = "Cloners";
	          IssueLinkManager linkMgr = ComponentAccessor.getIssueLinkManager();
	          for (IssueLink link : linkMgr.getInwardLinks(issueEvent.getIssue().getId()))
	          {
	        	  Issue clone = link.getSourceObject();  
	              if ((issueEvent.getComment().getGroupLevel() == null) && (issueEvent.getComment().getRoleLevel() == null))
	            {
	              CommentManager commentManager = ComponentAccessor.getCommentManager();
	              String comment = "Comment copied from Linked Issue : " + issueEvent.getComment().getBody();
	              commentManager.create(clone, issueEvent.getComment().getAuthorApplicationUser(), comment, true);
	              
	            }
	          }
	          for (IssueLink link : linkMgr.getOutwardLinks(issueEvent.getIssue().getId()))
	          {
	        	  Issue clone = link.getDestinationObject();
	        	if ((issueEvent.getComment().getGroupLevel() == null) && (issueEvent.getComment().getRoleLevel() == null))
	            {
	              CommentManager commentManager = ComponentAccessor.getCommentManager();
	              String comment = "Comment copied from Linked Issue : " + issueEvent.getComment().getBody();
	              commentManager.create(clone, issueEvent.getComment().getAuthorApplicationUser(), comment, true);
	            }
	          }
	        }
	      }
	      else if ((event instanceof UserEvent))
	      {
	        issueEvent = (IssueEvent)event;
	      }
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
	  
	  public void issueDeleted(IssueEvent event) {}
	  
	  public void issueMoved(IssueEvent event) {}
	  
	  public void issueWorkLogged(IssueEvent event) {}
	  
	  public void issueGenericEvent(IssueEvent event) {}
	  
	  public void customEvent(IssueEvent event) {}
	  
	  public void userSignup(UserEvent event) {}
	  
	  public void userCreated(UserEvent event) {}
	  
	  public void userForgotPassword(UserEvent event) {}
	  
	  public void userForgotUsername(UserEvent event) {}

	

	@Override
	public void userCannotChangePassword(UserEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
