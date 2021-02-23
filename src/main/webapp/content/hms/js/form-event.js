const isSlider = location.href.indexOf('slider');
	$(function() {
		Hms.editor.init('editor-box');
		
		$('[name=bgType]').change(function () {
			var id = $(this).attr('data-id');
			var v = $(this).val();
			$('[name=bgType][value=' + v + ']').trigger('click');
			$('.bgType').addClass('folding');
			$('.bgType#' + id).removeClass('folding');
		});

		$('.bg-delete').click( function() {
			var $div = $(this).parents('div.left');
			Hms.confirm('배경 이미지를 변경하시겠습니까?', function() {
				$div.find('.file-name').remove();
				$div.append('<input type="file" class="w100p bgType" id="bgImg" name="bgImg" _required="true" title="배경이미지"/>');
			});
		});
		
		fileAdd();
	});
	
	function save( formSelector) {
		Hms.confirm('저장하시겠습니까?', function() {
			if( Hms.editor.options.isInit == true) {
				$(formSelector).find('.eiditor-data').val( CKEDITOR.instances["editor-box"].getData());
			}
			if( Hms.formValidator(formSelector)) {
				$(formSelector).submit();
			}
		});
	}
	
	function fileAdd() {
		$('#sliderImg').change( function() {
			console.log($(this));
			var $file = $(this);
			var $ul = $(this).parents('ul');
			
			var $clone = $file.clone();
			var $li = $('<li></li>');
			$li.append($clone);
			$li.append('<i class="fa fa-times file-delete" aria-hidden="true"></i>');
			  
			$file.before($li);
			$clone.attr('name', 'sliderImg');
			$file.val('');
		});
		
		$(document).on('click', '.file-delete', function() {
			var fileLength = $('.file-delete').length;
			var $delete = $(this);
			
			if( fileLength == 1) {
				Hms.alert('파일은 하나이상 필수입니다.');
			} else {
				$delete.parents('li').remove();
			}
		});
		
		$('.li-delete').click( function() {
			var $delete = $(this);
			var deleteFiles = $('#deleteFiles').val();
			
			$('#deleteFiles').val(deleteFiles + ',' + $delete.parents('li').attr('data-file-name'));
			$delete.parents('li').remove();
		});
	}
	
	function selectComp( selector) {
		$('.comp-type').addClass('folding');
		$(`.${selector}`).removeClass('folding');
		
		$('.group-li').removeClass('active');
		$('.group-ul').find(`[data-selector="${selector}"]`).parents('li').addClass('active');
	}