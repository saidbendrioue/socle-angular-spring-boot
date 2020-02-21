package com.bycnit.socle.util;

import static com.bycnit.socle.enums.SearchOperation.SIMPLE_OPERATION_SET;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.bycnit.socle.dto.SearchCriteria;
import com.bycnit.socle.enums.SearchOperation;

/**
 * @author S.BENDRIOUE
 */
public class SearchCriteriaUtils {

    private static final Pattern pattern;

    static {
        final String operationSetExper = StringUtils.join(SIMPLE_OPERATION_SET, "|");
        pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
    }

    /**
     * Builds a list of search criterias using the search query
     *
     * @param searchToken
     *            search query
     * @return list of search criterias
     */
    public static List<SearchCriteria> build(final String searchToken) {

        final List<SearchCriteria> criterias = new ArrayList<>();

        if (StringUtils.isNotBlank(searchToken)) {
            final Matcher matcher = pattern.matcher(searchToken + ",");

            while (matcher.find()) {
                criterias.add(buildSearchCriteria(matcher));
            }
        }

        return criterias;
    }

    /**
     * Builds the search criteria using the matcher
     *
     * @param matcher
     *            a matcher instance
     * @return a search criteria instance
     */
    private static SearchCriteria buildSearchCriteria(final Matcher matcher) {

        final String key = matcher.group(1);
        final String operation = matcher.group(2);
        final Object value = matcher.group(4);
        final String prefix = matcher.group(3);
        final String suffix = matcher.group(5);

        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));

        if (op != null) {
            if (op == SearchOperation.EQUALITY) {
                final boolean startWithAsterisk = prefix.contains("*");
                final boolean endWithAsterisk = suffix.contains("*");

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }

            return new SearchCriteria(key, op, value);
        }

        throw new IllegalArgumentException(
                "Cannot create SearchCriteria with arguments" + String.format("[%s,%s,%s,%s,%s]", key, operation, value, prefix, suffix));
    }
}
