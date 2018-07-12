<?php
//https://book.cakephp.org/2.0/en/development/testing.html
class CapturesControllerTest extends ControllerTestCase {
    public $fixtures = array('plugin.security.user', 'plugin.main.boat', 'plugin.main.event', 'plugin.main.species', 'plugin.main.capture');

    public function testRestSync() {
        $user = array(array('User' => array('id' => 1)));
        $Captures = $this->generate('Main.Captures', array('components' => array('Security' => array('getRestUser'))));
        $Captures->Security->expects($this->any())->method('getRestUser')->will($this->returnValue($user));
        $modified = '0';
        $data = compact('modified');
        $method = 'post';
        $result = $this->testAction('/rest/main/captures/sync', compact('data', 'method'));
        debug($result);
    }
}
?>
