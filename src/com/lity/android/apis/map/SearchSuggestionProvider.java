package com.lity.android.apis.map;
import android.content.SearchRecentSuggestionsProvider;

public class SearchSuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.lity.android.apis.map.SearchSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;
    
    public SearchSuggestionProvider() {
        super();
        setupSuggestions(AUTHORITY, MODE);
    }
}