<?php
App::uses('MainAppController', 'Main.Controller');

class SpeciesController extends MainAppController {
    public $public = array('rest_sync');

    public function admin_index() {
        $species = $this->paginate();
        $this->set(compact('species'));
    }

    public function admin_add() {
        if ($this->request->is(array('post', 'put'))) {
            $this->request->data['Species']['user_id'] = $this->Auth->user('id');
            $this->Species->create();
            $species = $this->Species->save($this->request->data);
            if ($species) {
                $this->Session->setFlash(__d('style', 'New item added.'));
                $id = $species['Species']['id'];
                return $this->redirect("/admin/main/species/edit/{$id}");
            }
            else {
                $this->Session->setFlash(__d('style', 'Unable to add item.'));
            }
            return $this->redirect($this->referer());
        }
    }

    public function admin_edit($id) {
        if ($this->request->is(array('post', 'put'))) {
            if ($this->Species->save($this->request->data)) {
                $this->Session->setFlash(__d('style', 'Item updated.'));
            }
            else {
                $this->Session->setFlash(__d('style', 'Unable to update item.'));
            }
            return $this->redirect($this->referer());
        }
        $this->request->data = $this->Species->findById($id);
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
                    $content = $this->Species->find('rest', compact('user_id', 'modified'));
                } else {
                    $id = $this->request->data['id'];
                    $deleted = $this->request->data['deleted'];
                    $name = $this->request->data['name'];
                    $image = $this->request->data['image'];
                    $content = $this->Species->save(compact('id', 'user_id', 'deleted', 'name', 'image'));
                    unset($content['Species']['user_id']);
                    unset($content['Species']['created']);
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
