<?php
App::uses('MainAppModel', 'Main.Model');

class Event extends MainAppModel {
	public $belongsTo = array('Main.Boat');
	public $findMethods = array('rest' => TRUE);

    protected function _findRest($state, $query, $results = array()) {
        if ($state == 'before') {
            $user_id = $query['user_id'];
            $query['conditions']['Boat.user_id'] = $user_id;
            if (isset($query['modified'])) {
                $query['conditions']['Event.modified >'] = $query['modified'];
                $query['order'] = 'Event.modified ASC';
                $query['limit'] = 20;
            }
			$query['fields'] = array('Event.id', 'Event.modified', 'Event.deleted', 'Event.boat_id', 'Event.boat_name', 'Event.name');
            return $query;
        }
        return $results;
	}

	public function add($Event) {
		$result = FALSE;
		try {
			$this->create();
			$result = $this->save(compact('Event'));
		}
		catch (Exception $e) {
		}
		return $result;
	}
}
?>
