<?php
App::uses('MainAppController', 'Main.Controller');

class EventsController extends MainAppController {
    public $public = array('rest_sync');

    public function admin_index() {
        $events = $this->paginate();
        $this->set(compact('events'));
    }

    public function rest_sync() {
        $this->autoRender = FALSE;
        $content = array();
        if ($this->request->is('post')) {
            $user = $this->Security->getRestUser();
            if ($user) {
                $user_id = $user[0]['User']['id'];
                if (isset($this->request->data['modified'])) {
                    $modified = $this->request->data['modified'];
                    $content = $this->Event->find('rest', compact('user_id', 'modified'));
                } else {
                    $id = $this->request->data['id'];
                    $deleted = $this->request->data['deleted'];
                    $boat_id = $this->request->data['boat_id'];
                    $boat_name = $this->request->data['boat_name'];
                    $name = $this->request->data['name'];
                    $content = $this->Event->save(compact('id', 'deleted', 'boat_id', 'boat_name', 'name'));
                    unset($content['Event']['created']);
                    $content = array($content);
                }
            }
        }
        if (isset($this->params['requested'])) {
            return $content;
        }
        $content = json_encode($content);
        $this->response->body($content);
    }
}
?>
