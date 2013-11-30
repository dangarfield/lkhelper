<%@ tag body-content="scriptless"%>
<%@ attribute name="src" required="true"%>

<div class="img_wrapper">
	<div class="css_spinner">
	</div>
	<img
		src="${src}"
		onload="imgLoaded(this)"
		class="img-responsive"
		/>
</div>