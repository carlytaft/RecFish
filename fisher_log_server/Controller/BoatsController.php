<?php
App::uses('MainAppController', 'Main.Controller');

class BoatsController extends MainAppController {
    public $public = array('rest_sync');

    public function admin_index() {
        $boats = $this->paginate();
        $this->set(compact('boats'));
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
                    $content = $this->Boat->find('rest', compact('user_id', 'modified'));
                } else {
                    $id = $this->request->data['id'];
                    $deleted = $this->request->data['deleted'];
                    $name = $this->request->data['name'];
                    $image = $this->request->data['image'];
                    $content = $this->Boat->save(compact('id', 'user_id', 'deleted', 'name', 'image'));
                    unset($content['Boat']['user_id']);
                    unset($content['Boat']['created']);
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
