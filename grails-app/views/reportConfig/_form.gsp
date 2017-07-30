<%@ page import="bletest.ReportConfig" %>



<div class="fieldcontain ${hasErrors(bean: reportConfigInstance, field: 'description', 'error')} required">
	<label for="description">
		<g:message code="reportConfig.description.label" default="Description" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="description" required="" value="${reportConfigInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reportConfigInstance, field: 'duration', 'error')} required">
	<label for="duration">
		<g:message code="reportConfig.duration.label" default="Duration" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="duration" required="" value="${reportConfigInstance?.duration}"/>
</div>

