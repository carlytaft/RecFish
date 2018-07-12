<?php
//https://book.cakephp.org/2.0/en/development/testing.html
class EventsControllerTest extends ControllerTestCase {
    public $fixtures = array('plugin.security.user', 'plugin.main.boat', 'plugin.main.event');

    public function testRestSync() {
        $user = array(array('User' => array('id' => 1)));
        $Events = $this->generate('Main.Events', array('components' => array('Security' => array('getRestUser'))));
        $Events->Security->expects($this->any())->method('getRestUser')->will($this->returnValue($user));
        $modified = '0';
        $data = compact('modified');
        $method = 'post';
        $result = $this->testAction('/rest/main/events/sync', compact('data', 'method'));
        debug($result);
    }
}
?>
