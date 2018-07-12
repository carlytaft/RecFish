<?php
App::uses('AppController', 'Controller');

class MainAppController extends AppController {
	public $components = array('Auth', 'Security.Security', 'DebugKit.Toolbar');
	public $layout = 'Style.default';
	public $helpers = array('Security.Config', 'Security.Link', 'Security.Gravatar');
	public $public = array();

	public function isAuthorized($user) {
		return $this->Security->isAuthorized($user);
	}
}
?>
