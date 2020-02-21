Bonjour ${user.firstName} ${user.lastName}

<#if (user.applications?size == 1) >
	Accès à l'application <strong>${user.applications[0].name}</strong>.
</#if>

<#if (user.applications?size > 1) >
	Accès aux applications suivantes :<br>
	
	<ul style="list-style-type: square;">
		<#list user.applications as application>
		 
		<li style="font-size: 14px; line-height: 16px; text-align: justify;"><strong><span
				style="font-size: 14px; line-height: 16px;">${application.name}</span></strong><br data-mce-bogus="1"></li>															
		</#list>
	</ul>
</#if>


<a href="${activationLink}" target="_blank"
	style="display: block; text-decoration: none; -webkit-text-size-adjust: none; text-align: center; color: #ffffff; background-color: #3AAEE0; border-radius: 4px; -webkit-border-radius: 4px; -moz-border-radius: 4px; max-width: 227px; width: 187px; width: auto; border-top: 0px solid transparent; border-right: 0px solid transparent; border-bottom: 0px solid transparent; border-left: 0px solid transparent; padding-top: 5px; padding-right: 20px; padding-bottom: 5px; padding-left: 20px; font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif; mso-border-alt: none">
	<span style="font-size: 16px; line-height: 32px;">Initialiser le mot de passe</span>
</a>