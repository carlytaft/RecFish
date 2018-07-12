<?php
App::uses('MainAppModel', 'Main.Model');

class Capture extends MainAppModel {
    public $belongsTo = array('Main.Event', 'Main.Species');
    public $findMethods = array('rest' => TRUE);

    protected function _findRest($state, $query, $results = array()) {
        if ($state == 'before') {
            $query['recursive'] = -1;
            $user_id = $query['user_id'];
            $query['contain']['Event']['Boat']['conditions']['Boat.user_id'] = $user_id;
            if (isset($query['modified'])) {
                $query['conditions']['Capture.modified >'] = $query['modified'];
                $query['order'] = 'Capture.modified ASC';
                $query['limit'] = 20;
            }
            $query['fields'] = array('Capture.id', 'Capture.modified', 'Capture.deleted', 'Capture.event_id', 'Capture.species_id', 'Capture.species_name', 'Capture.weight', 'Capture.price');
            return $query;
        }
        return $results;
    }

    public function add($Capture) {
        $result = FALSE;
        try {
            $this->create();
            $result = $this->save(compact('Capture'));
        }
        catch (Exception $e) {
        }
        return $result;
    }
}
?>
