package ar.com.simbya.jiraassistant.apiServices;

import ar.com.simbya.jiraassistant.models.IssueListModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface JiraSearchApi {

    @GET("rest/api/2/search")
    Call<IssueListModel> searchIssues(
            @Query("jql") String jql);
}
