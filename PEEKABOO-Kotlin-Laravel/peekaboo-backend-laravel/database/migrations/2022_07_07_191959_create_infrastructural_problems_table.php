<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateInfrastructuralProblemsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('infrastructural_problems', function (Blueprint $table) {
            $table->id();
            $table->double('latitude');
            $table->double('longitude');
            $table->integer('is_fixed');
            $table->integer('false_infra');
            $table->integer('rejected');
            $table->integer('pending');
            $table->integer('accepted');
            $table->string('type');
            // $table->foreign('type_id')->references('id')->on('types');
            $table->unsignedBigInteger('user_id');
            $table->foreign('user_id')->references('id')->on('users');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('infrastructural_problems');
    }
}
