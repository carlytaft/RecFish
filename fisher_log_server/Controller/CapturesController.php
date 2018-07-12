<?php
App::uses('MainAppController', 'Main.Controller');
App::uses('Folder', 'Utility');

class CapturesController extends MainAppController {
    public $public = array('index', 'rest_sync');

    public function index() {
        $recursive = 3;
        $order = 'Event.id DESC, Species.name ASC';
        $this->paginate = compact('recursive', 'order');
        $captures = $this->paginate();
        $this->set(compact('captures'));
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
                    $content = $this->Capture->find('rest', compact('user_id', 'modified'));
                } else {
                    $id = $this->request->data['id'];
                    $deleted = $this->request->data['deleted'];
                    $event_id = $this->request->data['event_id'];
                    $species_id = $this->request->data['species_id'];
                    $species_name = $this->request->data['species_name'];
                    $weight = $this->request->data['weight'];
                    $content = $this->Capture->save(compact('id', 'deleted', 'event_id', 'species_id', 'species_name', 'weight'));
                    unset($content['Capture']['created']);
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

    public function admin_download() {
        $recursive = 3;
        $order = 'Event.id DESC, Species.name ASC';
        $captures = $this->Capture->find('all', compact('recursive', 'order'));
        $folder = implode(DS, array(ROOT, 'app', 'Vendor', 'PHPExcel', 'Classes'));
        set_include_path(get_include_path() . PATH_SEPARATOR . $folder);
        include 'PHPExcel.php';
        $file = new PHPExcel();
        $i = 1;
        $j = -1;
        $file->getActiveSheet()->setCellValueByColumnAndRow(++$j, $i, 'date');
        $file->getActiveSheet()->setCellValueByColumnAndRow(++$j, $i, 'event');
        $file->getActiveSheet()->setCellValueByColumnAndRow(++$j, $i, 'boat');
        $file->getActiveSheet()->setCellValueByColumnAndRow(++$j, $i, 'species');
        $file->getActiveSheet()->setCellValueByColumnAndRow(++$j, $i, 'weight');
        $file->getActiveSheet()->setCellValueByColumnAndRow(++$j, $i, 'user');
        foreach ($captures as $capture) {
            ++$i;
            $j = -1;
            $file->getActiveSheet()->setCellValueByColumnAndRow(++$j, $i, $capture['Capture']['modified']);
            $file->getActiveSheet()->setCellValueByColumnAndRow(++$j, $i, $capture['Event']['name']);
            $file->getActiveSheet()->setCellValueByColumnAndRow(++$j, $i, $capture['Event']['boat_name']);
            $file->getActiveSheet()->setCellValueByColumnAndRow(++$j, $i, $capture['Capture']['species_name']);
            $file->getActiveSheet()->setCellValueByColumnAndRow(++$j, $i, $capture['Capture']['weight']);
            $file->getActiveSheet()->setCellValueByColumnAndRow(++$j, $i, $capture['Event']['Boat']['User']['email']);
        }
        $file = new PHPExcel_Writer_Excel2007($file);
        $folder = implode(DS, array(ROOT, 'app', 'tmp', 'cache', 'excel'));
        new Folder($folder, TRUE);
        $filename = $folder . DS . uniqid(date('Ymd_His') . '_') . '.xlsx';
        $file->save($filename);
        $download = TRUE;
        $name = basename($filename);
        $this->response->file($filename, compact('download', 'name'));
        return $this->response;
    }
}
?>
