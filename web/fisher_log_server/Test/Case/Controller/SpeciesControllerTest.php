<?php
//https://book.cakephp.org/2.0/en/development/testing.html
class SpeciesControllerTest extends ControllerTestCase {
    public $fixtures = array('plugin.security.user', 'plugin.main.species');

    public function testRestSync() {
        $user = array(array('User' => array('id' => 1)));
        $Species = $this->generate('Main.Species', array('components' => array('Security' => array('getRestUser'))));
        $Species->Security->expects($this->any())->method('getRestUser')->will($this->returnValue($user));
        $modified = '0';
        $data = compact('modified');
        $method = 'post';
        $result = $this->testAction('/rest/main/species/sync', compact('data', 'method'));
        debug($result);
    }
}
?>
