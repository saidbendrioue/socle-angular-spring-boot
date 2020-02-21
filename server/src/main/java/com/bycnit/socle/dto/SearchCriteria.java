package com.bycnit.socle.dto;

import java.io.Serializable;

import com.bycnit.socle.enums.SearchOperation;

import lombok.Data;
import lombok.NonNull;

/**
 * @author S.BENDRIOUE
 */
@Data
public class SearchCriteria implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** Search field */
    @NonNull
    private String key;

    /** Search operation */
    @NonNull
    private SearchOperation operation;

    /** Search value */
    @NonNull
    private Object value;

    public String getStringValue() {
        return (String) value;
    }
}
