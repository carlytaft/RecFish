<?php
App::uses('MainAppModel', 'Main.Model');

class Species extends MainAppModel {
	public $belongsTo = array('Security.User');
	public $validate = array('name' => array('rule' => 'notBlank'));
	public $findMethods = array('rest' => TRUE);

	protected function _findRest($state, $query, $results = array()) {
		if ($state == 'before') {
			$user_id = $query['user_id'];
			$query['conditions']['OR'] = array('Species.user_id' => $user_id, 'Species.stock' => '1');
			if (isset($query['modified'])) {
				$query['conditions']['Species.modified >'] = $query['modified'];
				$query['order'] = 'Species.modified ASC';
				$query['limit'] = 20;
			}
			$query['fields'] = array('Species.id', 'Species.modified', 'Species.deleted', 'Species.name', 'Species.image');
			return $query;
		}
		return $results;
	}

	public function add($Species) {
		$result = FALSE;
		try {
			$this->create();
			$result = $this->save(compact('Species'));
		}
		catch (Exception $e) {
		}
		return $result;
	}
}
?>
