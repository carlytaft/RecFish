<?php
//https://book.cakephp.org/2.0/en/development/testing.html
class BoatsControllerTest extends ControllerTestCase {
    public $fixtures = array('plugin.security.user', 'plugin.main.boat');

    public function testRestSync() {
        $user = array(array('User' => array('id' => 1)));
        $Boats = $this->generate('Main.Boats', array('components' => array('Security' => array('getRestUser'))));
        $Boats->Security->expects($this->any())->method('getRestUser')->will($this->returnValue($user));
        $modified = '0';
        $data = compact('modified');
        $method = 'post';
        $result = $this->testAction('/rest/main/boats/sync', compact('data', 'method'));
        debug($result);


        $Boats = $this->generate('Main.Boats', array('components' => array('Security' => array('getRestUser'))));

        $Boats->Security->expects($this->any())->method('getRestUser')->will($this->returnValue($user));

        $id = 0;
        $deleted = 0;
        $name = 'test';
        $image = NULL;
        $data = compact('id', 'deleted', 'name', 'image');
        $result = $this->testAction('/rest/main/boats/sync', compact('data', 'method'));
        debug($result);
    }
}
?>
