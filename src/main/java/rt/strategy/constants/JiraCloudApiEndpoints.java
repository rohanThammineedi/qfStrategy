package rt.strategy.constants;

public class JiraCloudApiEndpoints {
    public static final String JIRA_ADMIN_PERMISSION = "/rest/api/3/mypermissions?permissions=ADMINISTER";

    public static final String GET_JIRA_PROJECTS = "/rest/api/3/project";

    //    "/rest/webhooks/1.0/webhook";
//    public static final String CREATE_JIRA_WEBHOOK = "/rest/api/2/webhook";
    public static final String CREATE_JIRA_WEBHOOK = "/rest/webhooks/1.0/webhook";
    // public static final String CREATE_JIRA_WEBHOOK = "/rest/api/3/webhook";
    public static final String GET_ALL_USERS = "/rest/api/3/users/search";

    public static final String JIRA_ISSUE = "/rest/api/3/issue";

    public static final String JIRA_ISSUE_SEARCH = "/rest/api/3/search";

    // /rest/webhooks/1.0/webhook/
    public static final String GET_JIRA_WEBHOOKS = "/rest/webhooks/1.0/webhook";

    public static final String ALL_FIELDS = "/rest/api/3/field";

    public static final String JIRA_USER_PICKER = "/rest/api/3/user/picker?query=";

    public static final String JIRA_SPECIFIC_USER_SEARCH = "/rest/api/3/user?accountId=";
}
