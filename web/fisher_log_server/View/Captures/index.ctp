<div>
<?php foreach ($captures as $element): ?>
<div class="well well-lg">
<div class="row">
<div class="col-md-2">
<?php echo $element['Event']['Boat']['User']['username']; ?>
</div>
<div class="col-md-2">
<?php echo $element['Event']['Boat']['name']; ?>
<br />
<?php echo $element['Event']['name']; ?>
</div>
<div class="col-md-6">
<?php echo $element['Species']['name']; ?>
</div>
<div class="col-md-2">
<?php echo $element['Capture']['weight']; ?>
</div>
<div class="clearfix">

</div>
</div>
</div>
<?php endforeach; ?>
<?php echo $this->element('Style.paginator'); ?>
</div>
