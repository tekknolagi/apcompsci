ruby -rrack -e "include Rack;Handler::Thin.run Builder.new{run Directory.new''}"
