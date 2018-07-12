<?php
App::uses('MainAppModel', 'Main.Model');

class Boat extends MainAppModel {
    public $belongsTo = array('Security.User');
    public $validate = array(
        'name' => array('rule' => 'notBlank')
    );
    public $findMethods = array('rest' => TRUE);

    protected function _findRest($state, $query, $results = array()) {
        if ($state == 'before') {
            $user_id = $query['user_id'];
            $query['conditions']['Boat.user_id'] = $user_id;
            if (isset($query['modified'])) {
                $query['conditions']['Boat.modified >'] = $query['modified'];
                $query['order'] = 'Boat.modified ASC';
                $query['limit'] = 20;
            }
            $query['fields'] = array('Boat.id', 'Boat.modified', 'Boat.deleted', 'Boat.name', 'Boat.image');
            return $query;
        }
        return $results;
    }

    public function add($Boat) {
        $result = FALSE;
        try {
            $this->create();
            $result = $this->save(compact('Boat'));
        }
        catch (Exception $e) {
        }
        return $result;
    }
}
?>
