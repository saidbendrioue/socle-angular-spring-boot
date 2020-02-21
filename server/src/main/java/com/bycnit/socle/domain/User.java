package com.bycnit.socle.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * A declared application's User. Users who can access an application
 *
 * @author S.BENDRIOUE
 */
@Getter
@Setter
@Entity
@Table(name = "T_USER")
public class User extends AbstractAuditingEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USR_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/** User Id */
	private Long id;

	/** User first name */
	@Column(name = "USR_FIRST_NAME")
	private String firstName;

	/** User last name */
	@Column(name = "USR_LAST_NAME")
	private String lastName;

	/** Is the user active */
	@Column(name = "USR_IS_ACTIVE")
	private boolean active;

	/** User email address */
	@Column(name = "USR_EMAIL")
	private String email;
	
	/** User email address */
	@Column(name = "USR_USERNAME")
	private String username;
	
	/** User's password Hash */
	@Column(name = "USR_PASS_HASH")
	private String password;

	/** Team where the user belongs */
	@Column(name = "USR_TEAM")
	private String team;

	/** Role of the user in {col,rh,mngr}*/
	@Column(name = "USR_ROLE")
	private String role;

	/** name of the user's profile image */
	@Column(name = "USR_IMG_URL")
	private String imgUrl;
	
	@Column(name = "IS_ENABLED")
	private boolean enabled;

	@Column(name = "PWD_RESET_DATE")
	private Date lastPasswordResetDate;

    /**
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).toHashCode();
    }

    /**
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
		if(!(obj instanceof User)) {
			return false;
		}
        if (this == obj) {
            return true;
        }

        final User other = (User) obj;

        return new EqualsBuilder().append(this.getId(),other.getId()).isEquals();
    }

}
