GIT=$(shell cat GIT)
GIT_SERVER=ssh://git@gemlab04.ucsd.edu/home/git
GIT_SERVER=ssh://git@gemlab01.ucsd.edu/home/git

all-git:
	@echo Git module
	@echo GIT: $(GIT)

$(GIT)/debug_kit:
	[ -d $@ ] || (git clone https://github.com/cakephp/debug_kit.git $@ && cd $@ && git checkout tags/2.2.6)

$(GIT)/cp_style:
	[ -d $@ ] || (git clone $(GIT_SERVER)/$(notdir $@) $@)

$(GIT)/cp_security:
	[ -d $@ ] || (git clone $(GIT_SERVER)/$(notdir $@) $@)

$(GIT)/google-api-php-client:
	[ -d $@ ] || (git clone https://github.com/google/google-api-php-client.git $@)
