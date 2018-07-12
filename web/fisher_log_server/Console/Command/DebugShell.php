<?php
class DebugShell extends AppShell {
	public $uses = array('Security.User');

	public function main() {
		$user = $this->User->find('first');
		$token = $user['User']['token'];
		$timestamp = time();
		$secret = Configure::read('Security.salt');
		$hash = hash('sha256', "{$token}{$timestamp}{$secret}");
		$server = 'http://localhost/fisherlog';
		$command = "curl -d \"timestamp={$timestamp}&hash={$hash}\" {$server}/rest/main/species/sync";
		$this->out($command);
	}
}
?>
