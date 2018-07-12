<div>
<?php if ($this->Config->has('admin')): ?>
<div class="row">
<div class="col-md-12">
<h1 class="page-header">
<?php echo __d('main', 'Administration'); ?>
</h1>
</div>
</div>
<div class="row">
<div class="col-md-3">
<div class="thumbnail">
<br />
<br />
<center>
<a href="<?php echo Router::url('/admin/security/users', TRUE); ?>">
<i class="fa fa-user fa-5x">

</i>
</a>
</center>
<div class="caption">
<ul>
<li>
<h3>
<a href="<?php echo Router::url('/admin/security/users', TRUE); ?>">
<?php echo __d('main', 'Users'); ?>
</a>
</h3>
</li>
</ul>
</div>
</div>
</div>
<div class="col-md-3">
<div class="thumbnail">
<br />
<br />
<center>
<a href="<?php echo Router::url('/admin/main/boats', TRUE); ?>">
<i class="fa fa-anchor fa-5x">

</i>
</a>
</center>
<div class="caption">
<ul>
<li>
<h3>
<a href="<?php echo Router::url('/admin/main/boats', TRUE); ?>">
<?php echo __d('main', 'Boats'); ?>
</a>
</h3>
</li>
</ul>
</div>
</div>
</div>
<div class="col-md-3">
<div class="thumbnail">
<br />
<br />
<center>
<a href="<?php echo Router::url('/admin/main/species', TRUE); ?>">
<i class="fa fa-picture-o fa-5x">

</i>
</a>
</center>
<div class="caption">
<ul>
<li>
<h3>
<a href="<?php echo Router::url('/admin/main/species', TRUE); ?>">
<?php echo __d('main', 'Species'); ?>
</a>
</h3>
</li>
</ul>
</div>
</div>
</div>
<div class="col-md-3">
<div class="thumbnail">
<br />
<br />
<center>
<a href="<?php echo Router::url('/admin/main/captures', TRUE); ?>">
<i class="fa fa-pencil fa-5x">

</i>
</a>
</center>
<div class="caption">
<ul>
<li>
<h3>
<a href="<?php echo Router::url('/admin/main/captures/download', TRUE); ?>">
<?php echo __d('main', 'Captures'); ?>
</a>
</h3>
</li>
</ul>
</div>
</div>
</div>
</div>
<?php endif; ?>
</div>
